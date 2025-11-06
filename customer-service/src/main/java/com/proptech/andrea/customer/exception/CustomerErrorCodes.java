package com.proptech.andrea.customer.exception;

import com.andrea.proptech.core.exception.ErrorDefinition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomerErrorCodes implements ErrorDefinition {

    // --- Customer (2000-2099) ---
    CUSTOMER_NOT_FOUND(2000, "customer.not.found", "Customer not found"),
    CUSTOMER_FISCAL_CODE_IN_USE(2001, "customer.fiscalcode.inuse", "Fiscal code is already in use by another customer"),

    // --- Individual (2100-2199) ---
    CUSTOMER_INDIVIDUAL_NOT_FOUND(2100, "customer.individual.not.found", "Individual customer not found"),

    // --- Business (2200-2299) ---
    CUSTOMER_BUSINESS_NOT_FOUND(2200, "customer.business.not.found", "Business customer not found"),
    CUSTOMER_CONTACT_NOT_FOUND(2201, "customer.contact.not.found", "Customer contact not found for this business"),
    CUSTOMER_ADDRESS_NOT_FOUND(2202, "customer.address.not.found", "Customer address not found for this business");

    private final int code;
    private final String errorCode;
    private final String defaultMessage;
}
