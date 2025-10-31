package com.proptech.andrea.customer.address.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByIdAndBusinessCustomer_Id(Long id, Long businessCustomerId);
}
