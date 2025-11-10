package com.proptech.andrea.customer.exception;

import com.andrea.proptech.core.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
@Log4j2
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    private ResponseEntity<ErrorResponse> buildLocalizedError(
            BaseLocalizedException ex,
            HttpServletRequest request,
            HttpStatus status) {

        Locale locale = LocaleContextHolder.getLocale();

        String message = messageSource.getMessage(
                ex.getErrorCode(),
                ex.getArgs(),
                ex.getDefaultMessage(),
                locale
        );

        ErrorResponse errorResponse = ErrorResponse.builder()
                .instant(Instant.now())
                .message(message)
                .status(status.value())
                .error(status.getReasonPhrase())
                .path(request.getRequestURI())
                .appCode(ex.getCode())
                .build();

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex, HttpServletRequest request) {

        return buildLocalizedError(ex, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceInUseException.class)
    public ResponseEntity<ErrorResponse> handleResourceInUseException(
            ResourceInUseException ex, HttpServletRequest request) {

        return buildLocalizedError(ex, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(HttpServletRequest request) {
        BaseLocalizedException localizedEx = new BaseLocalizedException(CommonErrorCodes.ACCESS_DENIED) {
        };

        return buildLocalizedError(localizedEx, request, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();

        String errorDetails = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                    String msg = messageSource.getMessage(error, locale);
                    return "'" + error.getField() + "': " + msg;
                })
                .collect(Collectors.joining(", "));

        ErrorDefinition validationError = CommonErrorCodes.VALIDATION_FAILED;
        String localizedMessage = messageSource.getMessage(
                validationError.getErrorCode(),
                null,
                validationError.getDefaultMessage(),
                locale
        );

        ErrorResponse errorResponse = ErrorResponse.builder()
                .instant(Instant.now())
                .message(localizedMessage + " [" + errorDetails + "]")
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .path(request.getRequestURI())
                .appCode(validationError.getCode())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {

        log.error("Unhandled exception occurred", ex);

        BaseLocalizedException localizedEx = new BaseLocalizedException(CommonErrorCodes.INTERNAL_SERVER_ERROR) {
        };

        return buildLocalizedError(localizedEx, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
