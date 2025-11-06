package com.proptech.andrea.customer.customer.service;

import com.andrea.proptech.core.exception.ResourceNotFoundException;
import com.proptech.andrea.customer.customer.data.CustomerRepository;
import com.proptech.andrea.customer.customer.mapper.response.CustomerToCustomerResponseDtoMapper;
import com.proptech.andrea.customer.customer.web.dto.response.CustomerResponseDto;
import com.proptech.andrea.customer.exception.CustomerErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerToCustomerResponseDtoMapper customerToCustomerResponseDtoMapper;

    @Transactional(readOnly = true)
    public Page<CustomerResponseDto> getCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(customerToCustomerResponseDtoMapper);
    }


    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException(CustomerErrorCodes.CUSTOMER_NOT_FOUND, id);
        }

        customerRepository.deleteById(id);
    }


}
