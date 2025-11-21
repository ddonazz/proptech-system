package com.proptech.andrea.customer.customer.validation;

import com.andrea.proptech.core.exception.ResourceInUseException;
import com.proptech.andrea.customer.customer.data.CustomerRepository;
import com.proptech.andrea.customer.customer.data.business.BusinessCustomer;
import com.proptech.andrea.customer.customer.data.business.BusinessCustomerRepository;
import com.proptech.andrea.customer.customer.data.individual.IndividualCustomer;
import com.proptech.andrea.customer.customer.data.individual.IndividualCustomerRepository;
import com.proptech.andrea.customer.exception.CustomerErrorCodes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerValidatorTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private IndividualCustomerRepository individualCustomerRepository;
    @Mock
    private BusinessCustomerRepository businessCustomerRepository;

    @InjectMocks
    private CustomerValidator validator;

    @Test
    @DisplayName("New FiscalCode: Should throw if exists in Individual table")
    void validateNewFiscalCode_existsInIndividual_throwsException() {
        String fiscalCode = "RSSMRA80A01H501U";
        when(individualCustomerRepository.existsByFiscalCode(fiscalCode)).thenReturn(true);

        ResourceInUseException ex = assertThrows(ResourceInUseException.class,
                () -> validator.validateNewFiscalCode(fiscalCode));
        assertEquals(CustomerErrorCodes.CUSTOMER_FISCAL_CODE_IN_USE.getErrorCode(), ex.getErrorCode());
    }

    @Test
    @DisplayName("New FiscalCode: Should throw if exists in Business table")
    void validateNewFiscalCode_existsInBusiness_throwsException() {
        String fiscalCode = "01234567890";
        // Non esiste come individuale, ma esiste come business
        when(individualCustomerRepository.existsByFiscalCode(fiscalCode)).thenReturn(false);
        when(businessCustomerRepository.existsByFiscalCode(fiscalCode)).thenReturn(true);

        ResourceInUseException ex = assertThrows(ResourceInUseException.class,
                () -> validator.validateNewFiscalCode(fiscalCode));
        assertEquals(CustomerErrorCodes.CUSTOMER_FISCAL_CODE_IN_USE.getErrorCode(), ex.getErrorCode());
    }

    @Test
    @DisplayName("Update FiscalCode: Should succeed if code belongs to same user")
    void validateFiscalCodeOnUpdate_sameId_success() {
        String fiscalCode = "OLD_CODE";
        Long myId = 100L;

        IndividualCustomer self = new IndividualCustomer();
        self.setId(myId);

        when(individualCustomerRepository.findByFiscalCode(fiscalCode)).thenReturn(Optional.of(self));
        // Business repo non viene chiamato se trovato in individual, oppure ritorna empty

        assertDoesNotThrow(() -> validator.validateFiscalCodeOnUpdate(fiscalCode, myId));
    }

    @Test
    @DisplayName("Update FiscalCode: Should throw if code belongs to another Business user")
    void validateFiscalCodeOnUpdate_otherBusinessUser_throwsException() {
        String fiscalCode = "01234567890";
        Long myId = 100L;
        Long otherId = 200L;

        BusinessCustomer other = new BusinessCustomer();
        other.setId(otherId);

        when(individualCustomerRepository.findByFiscalCode(fiscalCode)).thenReturn(Optional.empty());
        when(businessCustomerRepository.findByFiscalCode(fiscalCode)).thenReturn(Optional.of(other));

        ResourceInUseException ex = assertThrows(ResourceInUseException.class,
                () -> validator.validateFiscalCodeOnUpdate(fiscalCode, myId));
        assertEquals(CustomerErrorCodes.CUSTOMER_FISCAL_CODE_IN_USE.getErrorCode(), ex.getErrorCode());
    }
}