package com.andrea.proptech.property.unit.data;

import com.andrea.proptech.property.property.data.Property;
import com.andrea.proptech.property.room.data.Room;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "units")
@Getter
@Setter
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_sequence")
    @SequenceGenerator(name = "unit_sequence", sequenceName = "UNIT_SEQUENCE", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @Column
    private Long ownerId;

    @Column(nullable = false)
    private String internalName;

    /**
     * Codice identificativo univoco interno (SKU) per questa unità.
     * Es: "P-001"
     */
    @Column(unique = true)
    private String internalCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitType type;

    private String floor;
    private String internalNumber;

    private Double totalAreaMq;
    private Double walkableAreaMq;
    private Integer roomCount;
    private Integer bedroomCount;
    private Integer bathroomCount;
    private Integer maxOccupancy;

    @Column(unique = true)
    private String regionalIdentifierCode;

    @OneToOne(mappedBy = "unit", cascade = CascadeType.ALL)
    private EnergyCertificate energyCertificate;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CadastralData> cadastralData;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Room> rooms;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "unit_amenities", joinColumns = @JoinColumn(name = "unit_id"))
    @Enumerated(EnumType.STRING)
    private Set<UnitAmenity> amenities;
}