package com.proptech.andrea.customer.customer.mapper;

import com.proptech.andrea.customer.customer.data.BusinessCustomer;
import com.proptech.andrea.customer.customer.data.Customer;
import com.proptech.andrea.customer.customer.data.PrivateCustomer;
import com.proptech.andrea.customer.customer.web.dto.response.CustomerResponseDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CustomerToCustomerResponseDtoMapper implements Function<Customer, CustomerResponseDto> {

    @Override
    public CustomerResponseDto apply(Customer customer) {
        String displayName;

        if (customer instanceof PrivateCustomer privateCustomer) {
            displayName = privateCustomer.getFirstName() + " " + privateCustomer.getLastName();
        } else if (customer instanceof BusinessCustomer businessCustomer) {
            displayName = businessCustomer.getCompanyName();
        } else {
            displayName = customer.getEmail();
        }

        return CustomerResponseDto.builder()
                .id(customer.getId())
                .customerType(customer.getCustomerType())
                .email(customer.getEmail())
                .displayName(displayName)
                .build();
    }
}
