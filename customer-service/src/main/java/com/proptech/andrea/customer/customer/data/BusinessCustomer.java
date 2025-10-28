package com.proptech.andrea.customer.customer.data;

import com.proptech.andrea.customer.address.data.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "customers_business")
@DiscriminatorValue("BUSINESS")
@Getter
@Setter
@NoArgsConstructor
public class BusinessCustomer extends Customer {

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false, unique = true)
    private String vatNumber;

    @Column(unique = true)
    private String fiscalCode;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "legal_address_id")
    private Address legalAddress;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "billing_address_id")
    private Address billingAddress;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "business_customer_id")
    private Set<Address> operationalAddresses;

    @Column(length = 7)
    private String sdiCode;

    @Column
    private String pecEmail;

    @OneToMany(mappedBy = "businessCustomer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CustomerContact> contacts;

}
