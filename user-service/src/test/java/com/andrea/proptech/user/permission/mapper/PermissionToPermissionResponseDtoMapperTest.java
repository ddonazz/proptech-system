package com.andrea.proptech.user.permission.mapper;

import com.andrea.proptech.core.security.permission.PermissionAuthority;
import com.andrea.proptech.user.permission.data.Permission;
import com.andrea.proptech.user.permission.web.dto.response.PermissionResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PermissionToPermissionResponseDtoMapperTest {

    private final PermissionToPermissionResponseDtoMapper mapper = new PermissionToPermissionResponseDtoMapper();

    @Test
    @DisplayName("Should correctly map a Permission entity to a PermissionDto")
    void apply_withValidPermission_returnsCorrectDto() {
        Permission permissionEntity = new Permission(PermissionAuthority.USER_READ);

        PermissionResponseDto resultDto = mapper.apply(permissionEntity);

        assertNotNull(resultDto, "The resulting DTO should not be null.");
        assertEquals(PermissionAuthority.USER_READ.getAuthority(), resultDto.authority(), "The name was not mapped correctly.");
        assertEquals(PermissionAuthority.USER_READ.getDescription(), resultDto.description(), "The description was not mapped correctly.");
    }

    @Test
    @DisplayName("Should throw NullPointerException if the input Permission is null")
    void apply_withNullPermission_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> mapper.apply(null),
                "Passing a null permission should throw a NullPointerException due to @NonNull.");
    }

    @Test
    @DisplayName("Should map correctly even with empty but non-null fields")
    void apply_withEmptyFields_returnsDtoWithEmptyFields() {
        Permission permissionEntity = new Permission(PermissionAuthority.USER_READ);

        PermissionResponseDto resultDto = mapper.apply(permissionEntity);

        assertNotNull(resultDto);
        assertEquals(PermissionAuthority.USER_READ.getAuthority(), resultDto.authority());
        assertEquals(PermissionAuthority.USER_READ.getDescription(), resultDto.description());
    }
}
