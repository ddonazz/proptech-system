package com.andrea.user.role.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Set<Role> findAllByNameIn(Set<String> roles);
}
