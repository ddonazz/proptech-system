package com.andrea.proptech.property.unit.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnergyCertificateRepository extends JpaRepository<EnergyCertificate, Long> {

    Optional<EnergyCertificate> findByUnitId(Long unitId);

    boolean existsByUnitId(Long unitId);

    void deleteByUnitId(Long unitId);
}