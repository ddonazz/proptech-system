package com.andrea.proptech.user.user.service;

import com.andrea.proptech.user.role.data.Role;
import com.andrea.proptech.user.role.data.RoleRepository;
import com.andrea.proptech.user.user.data.User;
import com.andrea.proptech.user.user.data.UserRepository;
import com.andrea.proptech.user.user.mapper.UserCreateDtoToUserMapper;
import com.andrea.proptech.user.user.mapper.UserToUserResponseDtoMapper;
import com.andrea.proptech.user.user.web.dto.request.UserCreateDto;
import com.andrea.proptech.user.user.web.dto.response.UserResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserCreateDtoToUserMapper createMapper;
    @Mock
    private UserToUserResponseDtoMapper responseMapper;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Create User: Should encode password and assign existing roles")
    void createUser_success() {
        UserCreateDto dto = new UserCreateDto("user", "mail@test.com", "rawPass", Set.of(1L, 99L));

        User user = new User();
        Role existingRole = new Role();
        existingRole.setId(1L);

        // Simuliamo che solo il ruolo 1L esista, il 99L viene ignorato dal repository
        when(roleRepository.findAllByIdIn(any())).thenReturn(new HashSet<>(Set.of(existingRole)));

        when(createMapper.apply(dto)).thenReturn(user);
        when(passwordEncoder.encode("rawPass")).thenReturn("encodedPass");
        when(userRepository.save(user)).thenReturn(user);
        when(responseMapper.apply(user)).thenReturn(mock(UserResponseDto.class));

        userService.createUser(dto);

        // Verifiche
        assertEquals("encodedPass", user.getPassword());
        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains(existingRole));
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Create User: Should handle null roles set gracefully")
    void createUser_nullRoles() {
        UserCreateDto dto = new UserCreateDto("user", "mail", "pass", null);
        User user = new User();

        when(createMapper.apply(dto)).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn("pass");
        when(userRepository.save(user)).thenReturn(user);

        userService.createUser(dto);

        // Verifica che non crashi e che i ruoli siano gestiti (vuoti)
        assertTrue(user.getRoles() == null || user.getRoles().isEmpty());
        verify(roleRepository, never()).findAllByIdIn(any());
    }
}