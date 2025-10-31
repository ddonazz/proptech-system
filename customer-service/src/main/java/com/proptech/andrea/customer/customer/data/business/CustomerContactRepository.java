package com.proptech.andrea.customer.customer.data.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerContactRepository extends JpaRepository<CustomerContact, Long> {
    Optional<CustomerContact> findByIdAndBusinessCustomer_Id(Long id, Long businessCustomerId);
}
