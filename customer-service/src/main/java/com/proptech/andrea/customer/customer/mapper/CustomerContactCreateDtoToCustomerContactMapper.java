package com.proptech.andrea.customer.customer.mapper;

import com.proptech.andrea.customer.customer.data.CustomerContact;
import com.proptech.andrea.customer.customer.web.dto.request.CustomerContactCreateDto;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CustomerContactCreateDtoToCustomerContactMapper implements Function<CustomerContactCreateDto, CustomerContact> {

    @Override
    public CustomerContact apply(@NonNull CustomerContactCreateDto dto) {
        CustomerContact contact = new CustomerContact();
        contact.setUserId(dto.userId());
        contact.setFirstName(dto.firstName());
        contact.setLastName(dto.lastName());
        contact.setEmail(dto.email());
        contact.setJobTitle(dto.jobTitle());
        return contact;
    }
}
