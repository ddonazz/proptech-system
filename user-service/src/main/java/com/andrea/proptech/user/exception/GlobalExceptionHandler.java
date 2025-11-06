package com.andrea.proptech.user.exception;

import com.andrea.proptech.core.exception.BaseLocalizedException;
import com.andrea.proptech.core.exception.ErrorResponse;
import com.andrea.proptech.core.exception.ResourceInUseException;
import com.andrea.proptech.core.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Locale;

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, HttpServletRequest request) {

        log.error("Unhandled exception occurred", ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .instant(Instant.now())
                .message("An unexpected error occurred. Please contact support.")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
