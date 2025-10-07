package com.andrea.proptech.user.permission.data;

import com.andrea.proptech.user.permission.PermissionName;
import com.andrea.proptech.user.role.data.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Permission {

    @Id
    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;

    public Permission(PermissionName permissionName) {
        this.name = permissionName.toString();
        this.description = permissionName.getDescription();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Permission that)) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

}
