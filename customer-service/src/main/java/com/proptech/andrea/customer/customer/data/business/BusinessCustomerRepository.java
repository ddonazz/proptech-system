package com.proptech.andrea.customer.customer.data.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessCustomerRepository extends JpaRepository<BusinessCustomer, Long> {
    boolean existsByFiscalCode(String fiscalCode);

    Optional<BusinessCustomer> findByFiscalCode(String fiscalCode);
}
