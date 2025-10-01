package com.andrea.user.permission.mapper;

import com.andrea.user.permission.PermissionName;
import com.andrea.user.permission.data.Permission;
import com.andrea.user.permission.web.dto.PermissionDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PermissionToPermissionDtoMapperTest {

    private final PermissionToPermissionDtoMapper mapper = new PermissionToPermissionDtoMapper();

    @Test
    @DisplayName("Should correctly map a Permission entity to a PermissionDto")
    void apply_withValidPermission_returnsCorrectDto() {
        Permission permissionEntity = new Permission(PermissionName.USER_READ);

        PermissionDto resultDto = mapper.apply(permissionEntity);

        assertNotNull(resultDto, "The resulting DTO should not be null.");
        assertEquals(PermissionName.USER_READ.name(), resultDto.name(), "The name was not mapped correctly.");
        assertEquals(PermissionName.USER_READ.getDescription(), resultDto.description(), "The description was not mapped correctly.");
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
        Permission permissionEntity = new Permission(PermissionName.USER_READ);
        permissionEntity.setDescription("");

        PermissionDto resultDto = mapper.apply(permissionEntity);

        assertNotNull(resultDto);
        assertEquals(PermissionName.USER_READ.name(), resultDto.name());
        assertEquals("", resultDto.description());
    }
}
