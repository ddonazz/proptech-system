package com.andrea.proptech.property.property.web;

import com.andrea.proptech.property.address.web.dto.request.AddressCreateDto;
import com.andrea.proptech.property.address.web.dto.request.AddressUpdateDto;
import com.andrea.proptech.property.config.SecurityConfig;
import com.andrea.proptech.property.property.data.PropertyType;
import com.andrea.proptech.property.property.service.PropertyService;
import com.andrea.proptech.property.property.web.dto.request.PropertyCreateDto;
import com.andrea.proptech.property.property.web.dto.request.PropertyUpdateDto;
import com.andrea.proptech.property.property.web.dto.response.PropertyResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PropertyController.class)
@Import(SecurityConfig.class)
@TestPropertySource(properties = {
        "spring.security.oauth2.resourceserver.jwt.issuer-uri=http://mock-auth-service"
})
class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PropertyService propertyService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    @DisplayName("POST /api/v1/properties - Success (201 Created)")
    void createProperty_success() throws Exception {
        // Given
        AddressCreateDto addressDto = new AddressCreateDto(
                "Via Roma", "1", "00100", "Roma", "Roma", "RM", "IT", null, null, null
        );
        PropertyCreateDto request = new PropertyCreateDto(
                "Nuova Residenza", PropertyType.BUILDING, addressDto, 2024, null
        );

        PropertyResponseDto response = PropertyResponseDto.builder().id(1L).name("Nuova Residenza").build();

        when(propertyService.createProperty(any(PropertyCreateDto.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/v1/properties")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_property:create")))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Nuova Residenza"));
    }

    @Test
    @DisplayName("POST /api/v1/properties - Forbidden (403) if wrong scope")
    void createProperty_forbidden() throws Exception {
        // Given
        AddressCreateDto addressDto = new AddressCreateDto(
                "Via Roma", "1", "00100", "Roma", "Roma", "RM", "IT", null, null, null
        );
        PropertyCreateDto request = new PropertyCreateDto(
                "Nuova Residenza", PropertyType.BUILDING, addressDto, 2024, null
        );

        // When & Then
        mockMvc.perform(post("/api/v1/properties")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_wrong:scope")))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());

        verify(propertyService, never()).createProperty(any());
    }

    @Test
    @DisplayName("POST /api/v1/properties - Bad Request (400) on Validation Error")
    void createProperty_validationError() throws Exception {
        // Given - DTO invalido (nome vuoto)
        AddressCreateDto addressDto = new AddressCreateDto(
                "Via Roma", "1", "00100", "Roma", "Roma", "RM", "IT", null, null, null
        );
        PropertyCreateDto invalidRequest = new PropertyCreateDto(
                "", PropertyType.BUILDING, addressDto, 2024, null
        );

        // When & Then
        mockMvc.perform(post("/api/v1/properties")
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_property:create")))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/properties/{id} - Success (200 OK)")
    void getPropertyById_success() throws Exception {
        // Given
        Long id = 1L;
        PropertyResponseDto response = PropertyResponseDto.builder().id(id).name("Existing").build();
        when(propertyService.getPropertyById(id)).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/v1/properties/{id}", id)
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_property:read"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    @DisplayName("PUT /api/v1/properties/{id} - Success (200 OK)")
    void updateProperty_success() throws Exception {
        // Given
        Long id = 1L;
        AddressUpdateDto addressDto = new AddressUpdateDto(
                "Via", "1", "00100", "City", "Mun", "PR", "IT", null, null, null
        );
        PropertyUpdateDto request = new PropertyUpdateDto(
                "Updated Name", PropertyType.VILLA, addressDto, 2000, Set.of()
        );

        PropertyResponseDto response = PropertyResponseDto.builder().id(id).name("Updated Name").build();

        when(propertyService.updateProperty(eq(id), any(PropertyUpdateDto.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(put("/api/v1/properties/{id}", id)
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_property:update")))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    @Test
    @DisplayName("DELETE /api/v1/properties/{id} - Success (204 No Content)")
    void deleteProperty_success() throws Exception {
        // Given
        Long id = 1L;
        doNothing().when(propertyService).deleteProperty(id);

        // When & Then
        mockMvc.perform(delete("/api/v1/properties/{id}", id)
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_property:delete")))
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(propertyService).deleteProperty(id);
    }
}