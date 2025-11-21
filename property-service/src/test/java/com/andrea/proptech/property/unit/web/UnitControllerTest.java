package com.andrea.proptech.property.unit.web;

import com.andrea.proptech.property.config.SecurityConfig;
import com.andrea.proptech.property.unit.data.UnitType;
import com.andrea.proptech.property.unit.service.UnitService;
import com.andrea.proptech.property.unit.web.dto.request.UnitCreateDto;
import com.andrea.proptech.property.unit.web.dto.request.UnitUpdateDto;
import com.andrea.proptech.property.unit.web.dto.response.UnitResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UnitController.class)
@Import(SecurityConfig.class)
@TestPropertySource(properties = {
        "spring.security.oauth2.resourceserver.jwt.issuer-uri=http://mock-auth-service"
})
class UnitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UnitService unitService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    @DisplayName("POST /properties/{propId}/units - Success (201 Created)")
    void createUnit_success() throws Exception {
        Long propId = 10L;
        UnitCreateDto request = new UnitCreateDto(
                "Int. 1", "P-001-01", UnitType.APARTMENT, "1", "1A",
                80.0, 75.0, 3, 1, 1, 4, "CIR-123", Set.of(), null, null, null
        );

        UnitResponseDto response = UnitResponseDto.builder()
                .id(100L)
                .propertyId(propId)
                .internalName("Int. 1")
                .build();

        when(unitService.createUnit(eq(propId), any(UnitCreateDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/properties/{propId}/units", propId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_property:create")))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.internalName").value("Int. 1"));
    }

    @Test
    @DisplayName("GET /properties/{propId}/units - Success (200 OK) with Pagination")
    void getAllUnitsForProperty_success() throws Exception {
        Long propId = 10L;
        UnitResponseDto unitDto = UnitResponseDto.builder().id(100L).internalName("Unit A").build();
        Page<UnitResponseDto> page = new PageImpl<>(List.of(unitDto), PageRequest.of(0, 10), 1);

        when(unitService.getAllUnitsForProperty(eq(propId), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/properties/{propId}/units", propId)
                        .param("page", "0")
                        .param("size", "10")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_property:read"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(100L))
                .andExpect(jsonPath("$.page.totalElements").value(1));
    }

    @Test
    @DisplayName("PUT /properties/{propId}/units/{unitId} - Success (200 OK)")
    void updateUnit_success() throws Exception {
        Long propId = 10L;
        Long unitId = 100L;

        UnitUpdateDto request = new UnitUpdateDto(
                "Updated Name", "CODE", UnitType.STUDIO, "2", "2B",
                50.0, 45.0, 2, 1, 1, 2, "CIR-NEW", Set.of()
        );

        UnitResponseDto response = UnitResponseDto.builder().id(unitId).internalName("Updated Name").build();

        when(unitService.updateUnit(eq(propId), eq(unitId), any(UnitUpdateDto.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/properties/{propId}/units/{unitId}", propId, unitId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_property:update")))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.internalName").value("Updated Name"));
    }

    @Test
    @DisplayName("DELETE /properties/{propId}/units/{unitId} - Success (204 No Content)")
    void deleteUnit_success() throws Exception {
        Long propId = 10L;
        Long unitId = 100L;

        doNothing().when(unitService).deleteUnit(propId, unitId);

        mockMvc.perform(delete("/api/v1/properties/{propId}/units/{unitId}", propId, unitId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_property:delete")))
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(unitService).deleteUnit(propId, unitId);
    }
}