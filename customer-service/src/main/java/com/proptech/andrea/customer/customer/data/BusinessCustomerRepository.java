package com.proptech.andrea.customer.customer.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessCustomerRepository extends JpaRepository<BusinessCustomer, Long> {
}
