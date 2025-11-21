package com.andrea.proptech.property.unit.web;

import com.andrea.proptech.property.config.SecurityConfig;
import com.andrea.proptech.property.unit.service.EnergyCertificateService;
import com.andrea.proptech.property.unit.web.dto.response.EnergyCertificateResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnergyCertificateController.class)
@Import(SecurityConfig.class)
@TestPropertySource(properties = "spring.security.oauth2.resourceserver.jwt.issuer-uri=http://mock-auth-service")
class EnergyCertificateControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private EnergyCertificateService service;
    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    @DisplayName("GET getEnergyCertificate - Success")
    void getEnergyCertificate_success() throws Exception {
        Long propId = 1L;
        Long unitId = 2L;

        when(service.getEnergyCertificate(propId, unitId)).thenReturn(
                EnergyCertificateResponseDto.builder().energyClass("A4").build()
        );

        mockMvc.perform(get("/api/v1/properties/{propId}/units/{unitId}/energy-certificate", propId, unitId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_property:read"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.energyClass").value("A4"));
    }
}