package com.proptech.andrea.customer.customer.mapper.request.individual;

import com.proptech.andrea.customer.address.mapper.AddressCreateDtoToAddressMapper;
import com.proptech.andrea.customer.customer.data.individual.IndividualCustomer;
import com.proptech.andrea.customer.customer.web.dto.request.individual.IndividualCustomerCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class IndividualCustomerCreateDtoToIndividualCustomerMapper implements Function<IndividualCustomerCreateDto, IndividualCustomer> {

    private final AddressCreateDtoToAddressMapper addressCreateDtoToAddressMapper;

    @Override
    public IndividualCustomer apply(@NonNull IndividualCustomerCreateDto dto) {
        IndividualCustomer customer = new IndividualCustomer();

        customer.setEmail(dto.email());
        customer.setPhoneNumber(dto.phoneNumber());

        customer.setFirstName(dto.firstName());
        customer.setLastName(dto.lastName());
        customer.setFiscalCode(dto.fiscalCode());
        customer.setBirthDate(dto.birthDate());
        customer.setBirthPlace(dto.birthPlace());
        customer.setNationality(dto.nationality());

        customer.setBillingAddress(addressCreateDtoToAddressMapper.apply(dto.billingAddress()));

        return customer;
    }

}
