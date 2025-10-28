package com.proptech.andrea.customer.customer.service;

import com.andrea.proptech.core.exception.ResourceNotFoundException;
import com.proptech.andrea.customer.address.data.Address;
import com.proptech.andrea.customer.address.data.AddressRepository;
import com.proptech.andrea.customer.address.mapper.AddressCreateDtoToAddressMapper;
import com.proptech.andrea.customer.address.web.dto.request.AddressCreateDto;
import com.proptech.andrea.customer.customer.data.*;
import com.proptech.andrea.customer.customer.mapper.*;
import com.proptech.andrea.customer.customer.web.dto.request.*;
import com.proptech.andrea.customer.customer.web.dto.response.BusinessCustomerResponseDto;
import com.proptech.andrea.customer.customer.web.dto.response.CustomerContactResponseDto;
import com.proptech.andrea.customer.customer.web.dto.response.CustomerResponseDto;
import com.proptech.andrea.customer.customer.web.dto.response.PrivateCustomerResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PrivateCustomerRepository privateCustomerRepository;
    private final BusinessCustomerRepository businessCustomerRepository;
    private final CustomerContactRepository customerContactRepository;
    private final AddressRepository addressRepository;

    private final CustomerToCustomerResponseDtoMapper customerToCustomerResponseDtoMapper;
    private final PrivateCustomerCreateDtoToPrivateCustomerMapper privateCustomerCreateDtoToPrivateCustomerMapper;
    private final PrivateCustomerToPrivateCustomerResponseDtoMapper privateCustomerToPrivateCustomerResponseDtoMapper;
    private final BusinessCustomerCreateDtoToBusinessCustomerMapper businessCustomerCreateDtoToBusinessCustomerMapper;
    private final BusinessCustomerToBusinessCustomerResponseDtoMapper businessCustomerToBusinessCustomerResponseDtoMapper;
    private final CustomerContactCreateDtoToCustomerContactMapper customerContactCreateDtoToCustomerContactMapper;
    private final CustomerContactToCustomerContactResponseDtoMapper customerContactToCustomerContactResponseDtoMapper;
    private final AddressCreateDtoToAddressMapper addressCreateDtoToAddressMapper;
    private final PrivateCustomerUpdateDtoToPrivateCustomerMapper privateCustomerUpdateDtoToPrivateCustomerMapper;
    private final BusinessCustomerUpdateDtoToBusinessCustomerMapper businessCustomerUpdateDtoToBusinessCustomerMapper;

    @Transactional(readOnly = true)
    public Page<CustomerResponseDto> getCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(customerToCustomerResponseDtoMapper);
    }

    @Transactional(readOnly = true)
    public PrivateCustomerResponseDto getPrivateCustomerById(Long id) {
        return privateCustomerRepository.findById(id)
                .map(privateCustomerToPrivateCustomerResponseDtoMapper)
                .orElseThrow(() -> new ResourceNotFoundException("PrivateCustomer not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public BusinessCustomerResponseDto getBusinessCustomerById(Long id) {
        return businessCustomerRepository.findById(id)
                .map(businessCustomerToBusinessCustomerResponseDtoMapper)
                .orElseThrow(() -> new ResourceNotFoundException("BusinessCustomer not found with id: " + id));
    }

    @Transactional
    public PrivateCustomerResponseDto createPrivateCustomer(PrivateCustomerCreateDto dto) {
        PrivateCustomer customer = privateCustomerCreateDtoToPrivateCustomerMapper.apply(dto);
        PrivateCustomer savedCustomer = privateCustomerRepository.save(customer);
        return privateCustomerToPrivateCustomerResponseDtoMapper.apply(savedCustomer);
    }

    @Transactional
    public BusinessCustomerResponseDto createBusinessCustomer(BusinessCustomerCreateDto dto) {
        BusinessCustomer customer = businessCustomerCreateDtoToBusinessCustomerMapper.apply(dto);
        BusinessCustomer savedCustomer = businessCustomerRepository.save(customer);
        return businessCustomerToBusinessCustomerResponseDtoMapper.apply(savedCustomer);
    }

    @Transactional
    public PrivateCustomerResponseDto updatePrivateCustomer(Long id, PrivateCustomerUpdateDto dto) {
        PrivateCustomer customer = retrievePrivateCustomer(id);
        PrivateCustomer customerToUpdate = privateCustomerUpdateDtoToPrivateCustomerMapper.apply(dto, customer);
        PrivateCustomer updatedCustomer = privateCustomerRepository.save(customerToUpdate);
        return privateCustomerToPrivateCustomerResponseDtoMapper.apply(updatedCustomer);
    }

    @Transactional
    public BusinessCustomerResponseDto updateBusinessCustomer(Long id, BusinessCustomerUpdateDto dto) {
        BusinessCustomer customer = retrieveBusinessCustomer(id);
        BusinessCustomer customerToUpdate = businessCustomerUpdateDtoToBusinessCustomerMapper.apply(dto, customer);
        BusinessCustomer updatedCustomer = businessCustomerRepository.save(customerToUpdate);
        return businessCustomerToBusinessCustomerResponseDtoMapper.apply(updatedCustomer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }

        customerRepository.deleteById(id);
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

    @Transactional
    public BusinessCustomerResponseDto updateBusinessLegalAddress(Long businessId, AddressCreateDto dto) {
        BusinessCustomer customer = retrieveBusinessCustomer(businessId);
        Address newAddress = addressCreateDtoToAddressMapper.apply(dto);
        addressRepository.save(newAddress);

        customer.setLegalAddress(newAddress);
        BusinessCustomer updatedCustomer = businessCustomerRepository.save(customer);
        return businessCustomerToBusinessCustomerResponseDtoMapper.apply(updatedCustomer);
    }

    @Transactional
    public PrivateCustomerResponseDto updatePrivateBillingAddress(Long customerId, AddressCreateDto dto) {
        PrivateCustomer customer = retrievePrivateCustomer(customerId);
        Address newAddress = addressCreateDtoToAddressMapper.apply(dto);
        addressRepository.save(newAddress);

        customer.setBillingAddress(newAddress);
        PrivateCustomer updatedCustomer = privateCustomerRepository.save(customer);
        return privateCustomerToPrivateCustomerResponseDtoMapper.apply(updatedCustomer);
    }

    private PrivateCustomer retrievePrivateCustomer(Long id) {
        return privateCustomerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PrivateCustomer not found with id: " + id));
    }

    private BusinessCustomer retrieveBusinessCustomer(Long id) {
        return businessCustomerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("BusinessCustomer not found with id: " + id));
    }

}
