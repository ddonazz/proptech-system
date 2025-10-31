package com.proptech.andrea.customer.customer.mapper.request.individual;

import com.proptech.andrea.customer.address.mapper.AddressUpdateDtoToAddressMapper;
import com.proptech.andrea.customer.customer.data.individual.IndividualCustomer;
import com.proptech.andrea.customer.customer.web.dto.request.individual.IndividualCustomerUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class IndividualCustomerUpdateDtoToIndividualCustomerMapper implements BiFunction<IndividualCustomerUpdateDto, IndividualCustomer, IndividualCustomer> {

    private final AddressUpdateDtoToAddressMapper addressUpdateDtoToAddressMapper;

    @Override
    public IndividualCustomer apply(@NonNull IndividualCustomerUpdateDto dto, @NonNull IndividualCustomer customer) {
        customer.setEmail(dto.email());
        customer.setPhoneNumber(dto.phoneNumber());

        customer.setFirstName(dto.firstName());
        customer.setLastName(dto.lastName());
        customer.setBirthDate(dto.birthDate());
        customer.setBirthPlace(dto.birthPlace());
        customer.setNationality(dto.nationality());

        customer.setBillingAddress(addressUpdateDtoToAddressMapper.apply(dto.billingAddress(), customer.getBillingAddress()));

        return customer;
    }

}
