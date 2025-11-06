package com.proptech.andrea.customer.customer.data.individual;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IndividualCustomerRepository extends JpaRepository<IndividualCustomer, Long> {
    boolean existsByFiscalCode(String fiscalCode);

    Optional<IndividualCustomer> findByFiscalCode(String fiscalCode);
}
