package com.andrea.proptech.property.property.data;

import com.andrea.proptech.property.address.data.Address;
import com.andrea.proptech.property.unit.data.Unit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "properties")
@Getter
@Setter
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "property_sequence")
    @SequenceGenerator(name = "property_sequence", sequenceName = "PROPERTY_SEQUENCE", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropertyType type;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    private Integer constructionYear;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "property_amenities", joinColumns = @JoinColumn(name = "property_id"))
    @Enumerated(EnumType.STRING)
    private Set<BuildingAmenity> amenities;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Unit> units;
}