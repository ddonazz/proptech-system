package com.andrea.proptech.user.role.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findAllByNameIn(Set<String> roles);

    Optional<Role> findByName(String roleName);
}
