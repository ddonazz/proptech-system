package com.andrea.proptech.property.unit.web;

import com.andrea.proptech.property.config.SecurityConfig;
import com.andrea.proptech.property.unit.service.CadastralDataService;
import com.andrea.proptech.property.unit.web.dto.request.CadastralDataCreateDto;
import com.andrea.proptech.property.unit.web.dto.response.CadastralDataResponseDto;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CadastralDataController.class)
@Import(SecurityConfig.class)
@TestPropertySource(properties = "spring.security.oauth2.resourceserver.jwt.issuer-uri=http://mock-auth-service")
class CadastralDataControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private CadastralDataService cadastralDataService;
    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    @DisplayName("POST createCadastralData - Success")
    void createCadastralData_success() throws Exception {
        Long propId = 1L;
        Long unitId = 2L;

        CadastralDataCreateDto request = new CadastralDataCreateDto(
                "F1", "P1", "S1", "A/2", "2", "5 vani", 500.0
        );

        when(cadastralDataService.createCadastralData(eq(propId), eq(unitId), any())).thenReturn(
                CadastralDataResponseDto.builder().id(100L).build()
        );

        mockMvc.perform(post("/api/v1/properties/{propId}/units/{unitId}/cadastral-data", propId, unitId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_property:create")))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}