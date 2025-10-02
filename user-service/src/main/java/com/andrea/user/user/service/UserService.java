package com.andrea.user.user.service;

import com.andrea.user.exception.ResourceNotFoundException;
import com.andrea.user.role.data.Role;
import com.andrea.user.role.data.RoleRepository;
import com.andrea.user.role.web.dto.RoleDto;
import com.andrea.user.user.data.User;
import com.andrea.user.user.data.UserRepository;
import com.andrea.user.user.mapper.UserDtoToUserMapper;
import com.andrea.user.user.mapper.UserToUserDtoMapper;
import com.andrea.user.user.web.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final UserToUserDtoMapper userToUserDtoMapper;
    private final UserDtoToUserMapper userDtoToUserMapper;

    @Transactional(readOnly = true)
    public UserDto getUser(Long id) {
        User user = retrieveUser(id);
        return userToUserDtoMapper.apply(user);
    }

    @Transactional(readOnly = true)
    public UserDto getUserByUsername(String username) {
        User user = retrieveUser(username);
        return userToUserDtoMapper.apply(user);
    }

    @Transactional(readOnly = true)
    public Page<UserDto> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userToUserDtoMapper);
    }

    @Transactional
    public UserDto createUser(UserDto userDto) {
        Set<Role> roles = computeRoles(userDto.roles());

        User user = userDtoToUserMapper.apply(userDto);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(userDto.password()));

        User userSaved = userRepository.save(user);
        return userToUserDtoMapper.apply(userSaved);
    }

    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = retrieveUser(id);
        Set<Role> roles = computeRoles(userDto.roles());

        user.setRoles(roles);

        User userSaved = userRepository.save(user);
        return userToUserDtoMapper.apply(userSaved);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private Set<Role> computeRoles(Collection<RoleDto> roleDtos) {
        if (roleDtos == null || roleDtos.isEmpty()) {
            return new HashSet<>();
        }

        return roleRepository.findAllByNameIn(roleDtos.stream()
                .map(roleDto -> String.valueOf(roleDto.name()))
                .collect(Collectors.toSet()));
    }

    private User retrieveUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User '" + id + "' not found."));
    }

    private User retrieveUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User '" + username + "' not found."));
    }
}
