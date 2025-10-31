package com.proptech.andrea.customer.customer.service;

import com.andrea.proptech.core.exception.ResourceNotFoundException;
import com.proptech.andrea.customer.customer.data.individual.IndividualCustomer;
import com.proptech.andrea.customer.customer.data.individual.IndividualCustomerRepository;
import com.proptech.andrea.customer.customer.mapper.request.individual.IndividualCustomerCreateDtoToIndividualCustomerMapper;
import com.proptech.andrea.customer.customer.mapper.request.individual.IndividualCustomerUpdateDtoToIndividualCustomerMapper;
import com.proptech.andrea.customer.customer.mapper.response.individual.IndividualCustomerToIndividualCustomerResponseDtoMapper;
import com.proptech.andrea.customer.customer.web.dto.request.individual.IndividualCustomerCreateDto;
import com.proptech.andrea.customer.customer.web.dto.request.individual.IndividualCustomerUpdateDto;
import com.proptech.andrea.customer.customer.web.dto.response.individual.IndividualCustomerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IndividualCustomerService {

    private final IndividualCustomerRepository individualCustomerRepository;

    private final IndividualCustomerCreateDtoToIndividualCustomerMapper individualCustomerCreateDtoToIndividualCustomerMapper;
    private final IndividualCustomerUpdateDtoToIndividualCustomerMapper individualCustomerUpdateDtoToIndividualCustomerMapper;
    private final IndividualCustomerToIndividualCustomerResponseDtoMapper individualCustomerToIndividualCustomerResponseDtoMapper;

    @Transactional(readOnly = true)
    public IndividualCustomerResponseDto getPrivateCustomerById(Long id) {
        return individualCustomerRepository.findById(id)
                .map(individualCustomerToIndividualCustomerResponseDtoMapper)
                .orElseThrow(() -> new ResourceNotFoundException("IndividualCustomer not found with id: " + id));
    }


    @Transactional
    public IndividualCustomerResponseDto createPrivateCustomer(IndividualCustomerCreateDto dto) {
        IndividualCustomer customer = individualCustomerCreateDtoToIndividualCustomerMapper.apply(dto);
        IndividualCustomer savedCustomer = individualCustomerRepository.save(customer);
        return individualCustomerToIndividualCustomerResponseDtoMapper.apply(savedCustomer);
    }


    @Transactional
    public IndividualCustomerResponseDto updatePrivateCustomer(Long id, IndividualCustomerUpdateDto dto) {
        IndividualCustomer customer = retrievePrivateCustomer(id);
        IndividualCustomer customerToUpdate = individualCustomerUpdateDtoToIndividualCustomerMapper.apply(dto, customer);
        IndividualCustomer updatedCustomer = individualCustomerRepository.save(customerToUpdate);
        return individualCustomerToIndividualCustomerResponseDtoMapper.apply(updatedCustomer);
    }

    private IndividualCustomer retrievePrivateCustomer(Long id) {
        return individualCustomerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("IndividualCustomer not found with id: " + id));
    }

}
