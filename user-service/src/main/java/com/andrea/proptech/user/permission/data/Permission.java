package com.andrea.proptech.user.permission.data;

import com.andrea.proptech.core.security.permission.PermissionAuthority;
import com.andrea.proptech.user.role.data.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_sequence")
    @SequenceGenerator(name = "permission_sequence", sequenceName = "PERMISSION_SEQUENCE")
    private Long id;

    @Column(unique = true, nullable = false)
    private String authority;

    @Column(nullable = false)
    private String description;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;

    public Permission(PermissionAuthority permissionAuthority) {
        this.authority = permissionAuthority.toString();
        this.description = permissionAuthority.getDescription();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Permission that)) return false;
        return Objects.equals(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(authority);
    }

}
