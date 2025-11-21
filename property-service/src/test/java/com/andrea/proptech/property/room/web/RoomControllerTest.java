package com.andrea.proptech.property.room.web;

import com.andrea.proptech.property.config.SecurityConfig;
import com.andrea.proptech.property.room.data.RoomType;
import com.andrea.proptech.property.room.service.RoomService;
import com.andrea.proptech.property.room.web.dto.request.RoomCreateDto;
import com.andrea.proptech.property.room.web.dto.response.RoomResponseDto;
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

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoomController.class)
@Import(SecurityConfig.class)
@TestPropertySource(properties = {
        "spring.security.oauth2.resourceserver.jwt.issuer-uri=http://mock-auth-service"
})
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RoomService roomService;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @Test
    @DisplayName("POST createRoom - Success (201 Created)")
    void createRoom_success() throws Exception {
        Long propId = 1L;
        Long unitId = 10L;

        RoomCreateDto request = new RoomCreateDto(
                RoomType.BEDROOM, 15.5, Set.of("Parquet", "Finestra Panoramica")
        );

        RoomResponseDto response = RoomResponseDto.builder()
                .id(100L)
                .areaMq(15.5)
                .build();

        when(roomService.createRoom(eq(propId), eq(unitId), any(RoomCreateDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/properties/{propId}/units/{unitId}/rooms", propId, unitId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_property:create")))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.areaMq").value(15.5));
    }

    @Test
    @DisplayName("GET getAllRoomsForUnit - Success (200 OK)")
    void getAllRoomsForUnit_success() throws Exception {
        Long propId = 1L;
        Long unitId = 10L;

        RoomResponseDto room1 = RoomResponseDto.builder().id(101L).build();
        RoomResponseDto room2 = RoomResponseDto.builder().id(102L).build();

        when(roomService.getAllRoomsForUnit(propId, unitId)).thenReturn(List.of(room1, room2));

        mockMvc.perform(get("/api/v1/properties/{propId}/units/{unitId}/rooms", propId, unitId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_property:read"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(101));
    }

    @Test
    @DisplayName("DELETE deleteRoom - Success (204 No Content)")
    void deleteRoom_success() throws Exception {
        Long propId = 1L;
        Long unitId = 10L;
        Long roomId = 100L;

        doNothing().when(roomService).deleteRoom(propId, unitId, roomId);

        mockMvc.perform(delete("/api/v1/properties/{propId}/units/{unitId}/rooms/{roomId}", propId, unitId, roomId)
                        .with(jwt().authorities(new SimpleGrantedAuthority("SCOPE_property:delete")))
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(roomService).deleteRoom(propId, unitId, roomId);
    }
}