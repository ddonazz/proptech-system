package com.proptech.andrea.customer.customer.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer_contacts")
@Getter
@Setter
@NoArgsConstructor
public class CustomerContact {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_sequence")
    @SequenceGenerator(name = "contact_sequence", sequenceName = "CONTACT_SEQUENCE", allocationSize = 1)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    private String jobTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_customer_id", nullable = false)
    private BusinessCustomer businessCustomer;

}
