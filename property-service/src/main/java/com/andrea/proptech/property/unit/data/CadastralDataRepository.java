package com.andrea.proptech.property.unit.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CadastralDataRepository extends JpaRepository<CadastralData, Long> {

    Optional<CadastralData> findByIdAndUnitId(Long id, Long unitId);

    List<CadastralData> findByUnitId(Long unitId);
}