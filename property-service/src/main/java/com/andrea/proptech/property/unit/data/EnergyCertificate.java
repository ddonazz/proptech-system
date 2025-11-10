package com.andrea.proptech.property.unit.data;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "energy_certificates")
@Getter
@Setter
public class EnergyCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ape_sequence")
    @SequenceGenerator(name = "ape_sequence", sequenceName = "APE_SEQUENCE", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false, unique = true)
    private Unit unit;

    /**
     * Codice identificativo univoco del certificato.
     */
    @Column(unique = true)
    private String certificateIdentifier;

    @Column(nullable = false, length = 10)
    private String energyClass; // Es: "A4", "B", "G"

    /**
     * Indice di prestazione energetica globale (EPgl,nren).
     */
    private Double globalPerformanceIndex;

    private LocalDate issueDate; // Data di rilascio
    private LocalDate expiryDate; // Data di scadenza (di solito 10 anni)
}