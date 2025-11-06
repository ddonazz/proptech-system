package com.proptech.andrea.customer.customer.validation;

import com.andrea.proptech.core.exception.ResourceInUseException;
import com.proptech.andrea.customer.customer.data.business.BusinessCustomerRepository;
import com.proptech.andrea.customer.customer.data.individual.IndividualCustomerRepository;
import com.proptech.andrea.customer.exception.CustomerErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerValidator {

    private final IndividualCustomerRepository individualCustomerRepository;
    private final BusinessCustomerRepository businessCustomerRepository;

    public void validateNewFiscalCode(String fiscalCode) {
        if (fiscalCode == null || fiscalCode.isBlank()) {
            return;
        }

        if (individualCustomerRepository.existsByFiscalCode(fiscalCode) ||
                businessCustomerRepository.existsByFiscalCode(fiscalCode)) {

            throw new ResourceInUseException(CustomerErrorCodes.CUSTOMER_FISCAL_CODE_IN_USE, fiscalCode);
        }
    }

    public void validateFiscalCodeOnUpdate(String fiscalCode, Long customerIdToExclude) {
        if (fiscalCode == null || fiscalCode.isBlank()) {
            return;
        }

        individualCustomerRepository.findByFiscalCode(fiscalCode)
                .ifPresent(customer -> {
                    if (!customer.getId().equals(customerIdToExclude)) {
                        throw new ResourceInUseException(CustomerErrorCodes.CUSTOMER_FISCAL_CODE_IN_USE, fiscalCode);
                    }
                });

        businessCustomerRepository.findByFiscalCode(fiscalCode)
                .ifPresent(customer -> {
                    if (!customer.getId().equals(customerIdToExclude)) {
                        throw new ResourceInUseException(CustomerErrorCodes.CUSTOMER_FISCAL_CODE_IN_USE, fiscalCode);
                    }
                });
    }
}