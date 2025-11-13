package com.andrea.proptech.property.unit.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long>, JpaSpecificationExecutor<Unit> {

    Optional<Unit> findByIdAndPropertyId(Long id, Long propertyId);

    Page<Unit> findByPropertyId(Long propertyId, Pageable pageable);
}
