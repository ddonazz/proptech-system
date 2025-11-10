package com.proptech.andrea.customer.customer.data;

import com.proptech.andrea.customer.customer.data.business.BusinessCustomer;
import com.proptech.andrea.customer.customer.data.individual.IndividualCustomer;
import com.proptech.andrea.customer.customer.web.dto.request.CustomerFilters;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

public record CustomerSpecification(CustomerFilters customerFilters) implements Specification<Customer> {

    @Override
    public Predicate toPredicate(@NonNull Root<Customer> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        // --- Filtro Nome (name) ---
        if (customerFilters.name() != null && !customerFilters.name().isBlank()) {
            String pattern = "%" + customerFilters.name().toLowerCase() + "%";

            Path<String> firstName = cb.treat(root, IndividualCustomer.class).get("firstName");
            Path<String> lastName = cb.treat(root, IndividualCustomer.class).get("lastName");
            Path<String> companyName = cb.treat(root, BusinessCustomer.class).get("companyName");

            Predicate individualMatch = cb.or(
                    cb.like(cb.lower(firstName), pattern),
                    cb.like(cb.lower(lastName), pattern)
            );
            Predicate businessMatch = cb.like(cb.lower(companyName), pattern);

            predicates.add(cb.or(
                    cb.and(cb.equal(root.get("customerType"), CustomerType.INDIVIDUAL), individualMatch),
                    cb.and(cb.equal(root.get("customerType"), CustomerType.BUSINESS), businessMatch)
            ));
        }

        // --- Filtro Email ---
        if (customerFilters.email() != null && !customerFilters.email().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("email")), "%" + customerFilters.email().toLowerCase() + "%"));
        }

        // --- Filtro Codice Fiscale ---
        if (customerFilters.fiscalCode() != null && !customerFilters.fiscalCode().isBlank()) {
            String pattern = "%" + customerFilters.fiscalCode().toLowerCase() + "%";
            Path<String> individualFiscalCode = cb.treat(root, IndividualCustomer.class).get("fiscalCode");
            Path<String> businessFiscalCode = cb.treat(root, BusinessCustomer.class).get("fiscalCode");

            predicates.add(cb.or(
                    cb.like(cb.lower(individualFiscalCode), pattern),
                    cb.like(cb.lower(businessFiscalCode), pattern)
            ));
        }

        // --- Filtro Tipo Cliente ---
        if (customerFilters.customerType() != null) {
            predicates.add(cb.equal(root.get("customerType"), customerFilters.customerType()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}
