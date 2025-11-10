package com.proptech.andrea.customer.customer.service;

import com.andrea.proptech.core.exception.ResourceNotFoundException;
import com.proptech.andrea.customer.customer.data.Customer;
import com.proptech.andrea.customer.customer.data.CustomerRepository;
import com.proptech.andrea.customer.customer.data.CustomerSpecification;
import com.proptech.andrea.customer.customer.mapper.response.CustomerToCustomerResponseDtoMapper;
import com.proptech.andrea.customer.customer.web.dto.request.CustomerFilters;
import com.proptech.andrea.customer.customer.web.dto.response.CustomerResponseDto;
import com.proptech.andrea.customer.exception.CustomerErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerToCustomerResponseDtoMapper customerToCustomerResponseDtoMapper;

    @Transactional(readOnly = true)
    public Page<CustomerResponseDto> getCustomers(CustomerFilters filters, Pageable pageable) {
        Specification<Customer> spec = new CustomerSpecification(filters);
        return customerRepository.findAll(spec, pageable)
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
