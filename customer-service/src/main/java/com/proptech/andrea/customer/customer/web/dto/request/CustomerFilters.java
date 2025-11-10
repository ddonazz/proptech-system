package com.proptech.andrea.customer.customer.web.dto.request;

import com.proptech.andrea.customer.customer.data.CustomerType;

public record CustomerFilters(
        String name,
        String email,
        String fiscalCode,
        CustomerType customerType
) {
}
