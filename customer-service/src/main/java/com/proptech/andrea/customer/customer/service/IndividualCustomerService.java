package com.proptech.andrea.customer.customer.service;

import com.andrea.proptech.core.exception.ResourceNotFoundException;
import com.proptech.andrea.customer.customer.data.individual.IndividualCustomer;
import com.proptech.andrea.customer.customer.data.individual.IndividualCustomerRepository;
import com.proptech.andrea.customer.customer.mapper.request.individual.IndividualCustomerCreateDtoToIndividualCustomerMapper;
import com.proptech.andrea.customer.customer.mapper.request.individual.IndividualCustomerUpdateDtoToIndividualCustomerMapper;
import com.proptech.andrea.customer.customer.mapper.response.individual.IndividualCustomerToIndividualCustomerResponseDtoMapper;
import com.proptech.andrea.customer.customer.validation.CustomerValidator;
import com.proptech.andrea.customer.customer.web.dto.request.individual.IndividualCustomerCreateDto;
import com.proptech.andrea.customer.customer.web.dto.request.individual.IndividualCustomerUpdateDto;
import com.proptech.andrea.customer.customer.web.dto.response.individual.IndividualCustomerResponseDto;
import com.proptech.andrea.customer.exception.CustomerErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class IndividualCustomerService {

    private final IndividualCustomerRepository individualCustomerRepository;

    private final IndividualCustomerCreateDtoToIndividualCustomerMapper individualCustomerCreateDtoToIndividualCustomerMapper;
    private final IndividualCustomerUpdateDtoToIndividualCustomerMapper individualCustomerUpdateDtoToIndividualCustomerMapper;
    private final IndividualCustomerToIndividualCustomerResponseDtoMapper individualCustomerToIndividualCustomerResponseDtoMapper;

    private final CustomerValidator customerValidator;

    @Transactional(readOnly = true)
    public IndividualCustomerResponseDto getPrivateCustomerById(Long id) {
        return individualCustomerRepository.findById(id)
                .map(individualCustomerToIndividualCustomerResponseDtoMapper)
                .orElseThrow(() -> new ResourceNotFoundException(CustomerErrorCodes.CUSTOMER_INDIVIDUAL_NOT_FOUND, id));
    }


    @Transactional
    public IndividualCustomerResponseDto createPrivateCustomer(IndividualCustomerCreateDto dto) {
        customerValidator.validateNewFiscalCode(dto.fiscalCode());
        customerValidator.validateNewEmail(dto.email());

        IndividualCustomer customer = individualCustomerCreateDtoToIndividualCustomerMapper.apply(dto);
        IndividualCustomer savedCustomer = individualCustomerRepository.save(customer);
        return individualCustomerToIndividualCustomerResponseDtoMapper.apply(savedCustomer);
    }


    @Transactional
    public IndividualCustomerResponseDto updatePrivateCustomer(Long id, IndividualCustomerUpdateDto dto) {
        IndividualCustomer customer = retrievePrivateCustomer(id);

        if (dto.email() != null && !Objects.equals(customer.getEmail(), dto.email())) {
            customerValidator.validateEmailOnUpdate(dto.email(), id);
        }
        
        IndividualCustomer customerToUpdate = individualCustomerUpdateDtoToIndividualCustomerMapper.apply(dto, customer);
        IndividualCustomer updatedCustomer = individualCustomerRepository.save(customerToUpdate);
        return individualCustomerToIndividualCustomerResponseDtoMapper.apply(updatedCustomer);
    }

    private IndividualCustomer retrievePrivateCustomer(Long id) {
        return individualCustomerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CustomerErrorCodes.CUSTOMER_INDIVIDUAL_NOT_FOUND, id));
    }

}
