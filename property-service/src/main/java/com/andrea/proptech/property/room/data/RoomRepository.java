package com.andrea.proptech.property.room.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByIdAndUnitId(Long id, Long unitId);

    List<Room> findByUnitId(Long unitId);
}