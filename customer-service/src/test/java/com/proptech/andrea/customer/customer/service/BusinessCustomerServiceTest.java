package com.proptech.andrea.customer.customer.service;

import com.andrea.proptech.core.exception.ResourceNotFoundException;
import com.proptech.andrea.customer.address.mapper.AddressUpdateDtoToAddressMapper;
import com.proptech.andrea.customer.customer.data.business.BusinessCustomer;
import com.proptech.andrea.customer.customer.data.business.BusinessCustomerRepository;
import com.proptech.andrea.customer.customer.mapper.request.business.BusinessCustomerUpdateDtoToBusinessCustomerMapper;
import com.proptech.andrea.customer.customer.mapper.response.business.BusinessCustomerToBusinessCustomerResponseDtoMapper;
import com.proptech.andrea.customer.customer.validation.CustomerValidator;
import com.proptech.andrea.customer.customer.web.dto.request.business.BusinessCustomerUpdateDto;
import com.proptech.andrea.customer.customer.web.dto.response.business.BusinessCustomerResponseDto;
import com.proptech.andrea.customer.exception.CustomerErrorCodes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusinessCustomerServiceTest {

    @Mock
    private BusinessCustomerRepository repository;
    @Mock
    private CustomerValidator validator;
    @Mock
    private BusinessCustomerUpdateDtoToBusinessCustomerMapper updateMapper;
    @Mock
    private BusinessCustomerToBusinessCustomerResponseDtoMapper responseMapper;
    // Mock necessario per l'iniezione delle dipendenze (anche se non usato direttamente nel test)
    @Mock
    private AddressUpdateDtoToAddressMapper addressMapper;

    @InjectMocks
    private BusinessCustomerService service;

    @Test
    @DisplayName("Update: Should validate changed fields and save")
    void updateBusinessCustomer_success() {
        Long id = 1L;
        BusinessCustomer existing = new BusinessCustomer();
        existing.setId(id);
        existing.setFiscalCode("OLD");
        existing.setEmail("old@test.com");

        BusinessCustomerUpdateDto dto = mock(BusinessCustomerUpdateDto.class);
        when(dto.fiscalCode()).thenReturn("NEW"); // Cambiato
        when(dto.email()).thenReturn("old@test.com"); // Invariato

        BusinessCustomer updated = new BusinessCustomer();

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(updateMapper.apply(dto, existing)).thenReturn(updated);
        when(repository.save(updated)).thenReturn(updated);
        when(responseMapper.apply(updated)).thenReturn(mock(BusinessCustomerResponseDto.class));

        service.updateBusinessCustomer(id, dto);

        // Verifica: validatore chiamato solo per CF
        verify(validator).validateFiscalCodeOnUpdate("NEW", id);
        verify(validator, never()).validateEmailOnUpdate(anyString(), anyLong());
        verify(repository).save(updated);
    }

    @Test
    @DisplayName("Update: Should throw exception if ID not found")
    void updateBusinessCustomer_notFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class,
                () -> service.updateBusinessCustomer(99L, mock(BusinessCustomerUpdateDto.class)));

        assertEquals(CustomerErrorCodes.CUSTOMER_BUSINESS_NOT_FOUND.getErrorCode(), ex.getErrorCode());
    }
}