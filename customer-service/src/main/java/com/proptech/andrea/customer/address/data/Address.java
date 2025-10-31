package com.proptech.andrea.customer.address.data;

import com.proptech.andrea.customer.customer.data.business.BusinessCustomer;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "customers_addresses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_sequence")
    @SequenceGenerator(name = "address_sequence", sequenceName = "ADDRESS_SEQUENCE", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false, length = 50)
    private String number;

    @Column(nullable = false, length = 10)
    private String postalCode;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(length = 100)
    private String province;

    @Column(nullable = false, length = 2)
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_customer_id")
    private BusinessCustomer businessCustomer;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Address address)) return false;
        return Objects.equals(id, address.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
