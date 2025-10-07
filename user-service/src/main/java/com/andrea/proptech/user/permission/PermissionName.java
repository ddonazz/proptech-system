package com.andrea.proptech.user.permission;

public enum PermissionName {

    USER_READ("Read user"),
    USER_CREATE("Create user"),
    USER_UPDATE("Update user"),
    USER_DELETE("Delete user"),

    ROLE_READ("Read role"),
    ROLE_CREATE("Create role"),
    ROLE_UPDATE("Update role"),
    ROLE_DELETE("Delete role"),

    PERMISSION_READ("Read permission"),

    ADMIN_ACCESS("Admin access");

    PermissionName(String description) {
        this.description = description;
    }

    private final String description;

    public String getDescription() {
        return description;
    }

}
