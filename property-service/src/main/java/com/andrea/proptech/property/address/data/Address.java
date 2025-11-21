package com.andrea.proptech.property.address.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
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

    @Column(nullable = false, length = 100)
    private String municipality;

    @Column(nullable = false, length = 2)
    private String province;

    @Column(nullable = false, length = 2)
    private String country;

    private Double latitude;
    private Double longitude;

    private String notes;
}
