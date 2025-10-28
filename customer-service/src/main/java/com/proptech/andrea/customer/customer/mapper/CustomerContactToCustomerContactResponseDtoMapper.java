package com.proptech.andrea.customer.customer.mapper;

import com.proptech.andrea.customer.customer.data.CustomerContact;
import com.proptech.andrea.customer.customer.web.dto.response.CustomerContactResponseDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CustomerContactToCustomerContactResponseDtoMapper implements Function<CustomerContact, CustomerContactResponseDto> {

    @Override
    public CustomerContactResponseDto apply(@NonNull CustomerContact contact) {
        return CustomerContactResponseDto.builder()
                .id(contact.getId())
                .userId(contact.getUserId())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .jobTitle(contact.getJobTitle())
                .build();
    }
}
