package com.proptech.andrea.customer.customer.service;

import com.andrea.proptech.core.exception.ResourceNotFoundException;
import com.proptech.andrea.customer.address.data.Address;
import com.proptech.andrea.customer.address.data.AddressRepository;
import com.proptech.andrea.customer.address.mapper.AddressCreateDtoToAddressMapper;
import com.proptech.andrea.customer.address.mapper.AddressToAddressResponseDtoMapper;
import com.proptech.andrea.customer.address.web.dto.request.AddressCreateDto;
import com.proptech.andrea.customer.address.web.dto.response.AddressResponseDto;
import com.proptech.andrea.customer.customer.data.business.BusinessCustomer;
import com.proptech.andrea.customer.customer.data.business.BusinessCustomerRepository;
import com.proptech.andrea.customer.customer.data.business.CustomerContact;
import com.proptech.andrea.customer.customer.data.business.CustomerContactRepository;
import com.proptech.andrea.customer.customer.mapper.request.business.BusinessCustomerCreateDtoToBusinessCustomerMapper;
import com.proptech.andrea.customer.customer.mapper.request.business.BusinessCustomerUpdateDtoToBusinessCustomerMapper;
import com.proptech.andrea.customer.customer.mapper.request.business.CustomerContactCreateDtoToCustomerContactMapper;
import com.proptech.andrea.customer.customer.mapper.response.business.BusinessCustomerToBusinessCustomerResponseDtoMapper;
import com.proptech.andrea.customer.customer.mapper.response.business.CustomerContactToCustomerContactResponseDtoMapper;
import com.proptech.andrea.customer.customer.web.dto.request.business.BusinessCustomerCreateDto;
import com.proptech.andrea.customer.customer.web.dto.request.business.BusinessCustomerUpdateDto;
import com.proptech.andrea.customer.customer.web.dto.request.business.CustomerContactCreateDto;
import com.proptech.andrea.customer.customer.web.dto.response.business.BusinessCustomerResponseDto;
import com.proptech.andrea.customer.customer.web.dto.response.business.CustomerContactResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BusinessCustomerService {

    private final BusinessCustomerRepository businessCustomerRepository;
    private final CustomerContactRepository customerContactRepository;
    private final AddressRepository addressRepository;

    private final BusinessCustomerCreateDtoToBusinessCustomerMapper businessCustomerCreateDtoToBusinessCustomerMapper;
    private final BusinessCustomerUpdateDtoToBusinessCustomerMapper businessCustomerUpdateDtoToBusinessCustomerMapper;
    private final BusinessCustomerToBusinessCustomerResponseDtoMapper businessCustomerToBusinessCustomerResponseDtoMapper;
    private final CustomerContactCreateDtoToCustomerContactMapper customerContactCreateDtoToCustomerContactMapper;
    private final CustomerContactToCustomerContactResponseDtoMapper customerContactToCustomerContactResponseDtoMapper;
    private final AddressCreateDtoToAddressMapper addressCreateDtoToAddressMapper;
    private final AddressToAddressResponseDtoMapper addressToAddressResponseDtoMapper;

    @Transactional(readOnly = true)
    public BusinessCustomerResponseDto getBusinessCustomerById(Long id) {
        return businessCustomerRepository.findById(id)
                .map(businessCustomerToBusinessCustomerResponseDtoMapper)
                .orElseThrow(() -> new ResourceNotFoundException("BusinessCustomer not found with id: " + id));
    }

    @Transactional
    public BusinessCustomerResponseDto createBusinessCustomer(BusinessCustomerCreateDto dto) {
        BusinessCustomer customer = businessCustomerCreateDtoToBusinessCustomerMapper.apply(dto);
        BusinessCustomer savedCustomer = businessCustomerRepository.save(customer);
        return businessCustomerToBusinessCustomerResponseDtoMapper.apply(savedCustomer);
    }

    @Transactional
    public BusinessCustomerResponseDto updateBusinessCustomer(Long id, BusinessCustomerUpdateDto dto) {
        BusinessCustomer customer = retrieveBusinessCustomer(id);
        BusinessCustomer customerToUpdate = businessCustomerUpdateDtoToBusinessCustomerMapper.apply(dto, customer);
        BusinessCustomer updatedCustomer = businessCustomerRepository.save(customerToUpdate);
        return businessCustomerToBusinessCustomerResponseDtoMapper.apply(updatedCustomer);
    }

    @Transactional
    public AddressResponseDto createOperationalAddressForBusiness(Long businessId, AddressCreateDto dto) {
        BusinessCustomer businessCustomer = retrieveBusinessCustomer(businessId);
        Address address = addressCreateDtoToAddressMapper.apply(dto);

        Address savedAddress = addressRepository.save(address);
        businessCustomer.getOperationalAddresses().add(savedAddress);

        return addressToAddressResponseDtoMapper.apply(savedAddress);
    }

    @Transactional
    public void deleteOperationalAddress(Long businessId, Long operationalAddressId) {
        Address operationalAddress = addressRepository.findByIdAndBusinessCustomer_Id(operationalAddressId, businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Operational address not found with id " + operationalAddressId + " for business " + businessId));

        addressRepository.delete(operationalAddress);
    }

    @Transactional
    public CustomerContactResponseDto createContactForBusiness(Long businessId, CustomerContactCreateDto dto) {
        BusinessCustomer businessCustomer = retrieveBusinessCustomer(businessId);
        CustomerContact contact = customerContactCreateDtoToCustomerContactMapper.apply(dto);
        contact.setBusinessCustomer(businessCustomer);

        CustomerContact savedContact = customerContactRepository.save(contact);
        return customerContactToCustomerContactResponseDtoMapper.apply(savedContact);
    }

    @Transactional
    public void deleteContact(Long businessId, Long contactId) {
        CustomerContact contact = customerContactRepository.findByIdAndBusinessCustomer_Id(contactId, businessId)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id " + contactId + " for business " + businessId));

        customerContactRepository.delete(contact);
    }

    private BusinessCustomer retrieveBusinessCustomer(Long id) {
        return businessCustomerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BusinessCustomer not found with id: " + id));
    }

}
