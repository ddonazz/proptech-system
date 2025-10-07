package com.andrea.proptech.user.permission.web;

import com.andrea.proptech.user.permission.PermissionName;
import com.andrea.proptech.user.permission.service.PermissionService;
import com.andrea.proptech.user.permission.web.dto.PermissionDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PermissionController.class)
public class PermissionControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PermissionService permissionService;

    @Test
    @DisplayName("GET /api/v1/permissions should return 200 OK with a page of permissions")
    void getAllPermissions_whenCalled_shouldReturnOkAndPageOfPermissions() throws Exception {
        PermissionDto permissionDto = PermissionDto.builder()
                .name(PermissionName.USER_READ.name())
                .description(PermissionName.USER_READ.getDescription())
                .build();

        Pageable pageable = PageRequest.of(0, 10);
        Page<PermissionDto> permissionPage = new PageImpl<>(List.of(permissionDto), pageable, 1);

        when(permissionService.findAll(any(Pageable.class))).thenReturn(permissionPage);

        mockMvc.perform(get("/api/v1/permissions")
                        .with(user("test-user"))
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.page.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].name").value(PermissionName.USER_READ.name()))
                .andExpect(jsonPath("$.content[0].description").value(PermissionName.USER_READ.getDescription()));
    }

    @Test
    @DisplayName("GET /api/v1/permissions should return 200 OK with an empty page when no permissions exist")
    void getAllPermissions_whenNoPermissionsExist_shouldReturnOkAndEmptyPage() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PermissionDto> emptyPage = new PageImpl<>(List.of(), pageable, 0);

        when(permissionService.findAll(any(Pageable.class))).thenReturn(emptyPage);

        mockMvc.perform(get("/api/v1/permissions")
                        .with(user("test-user"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.totalElements").value(0))
                .andExpect(jsonPath("$.content").isEmpty());
    }

}
