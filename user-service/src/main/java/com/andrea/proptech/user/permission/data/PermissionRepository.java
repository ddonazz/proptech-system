package com.andrea.proptech.user.permission.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
    Set<Permission> findAllByNameIn(Collection<String> names);
}
