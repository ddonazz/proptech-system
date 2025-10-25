package com.andrea.proptech.user.user.service;

import com.andrea.proptech.core.exception.ResourceNotFoundException;
import com.andrea.proptech.user.role.data.Role;
import com.andrea.proptech.user.role.data.RoleRepository;
import com.andrea.proptech.user.user.data.User;
import com.andrea.proptech.user.user.data.UserRepository;
import com.andrea.proptech.user.user.mapper.UserCreateDtoToUserMapper;
import com.andrea.proptech.user.user.mapper.UserToUserResponseDtoMapper;
import com.andrea.proptech.user.user.web.dto.request.UserCreateDto;
import com.andrea.proptech.user.user.web.dto.request.UserUpdateDto;
import com.andrea.proptech.user.user.web.dto.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final UserToUserResponseDtoMapper userToUserResponseDtoMapper;
    private final UserCreateDtoToUserMapper userCreateDtoToUserMapper;

    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long id) {
        User user = retrieveUser(id);
        return userToUserResponseDtoMapper.apply(user);
    }

    @Transactional(readOnly = true)
    public Page<UserResponseDto> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userToUserResponseDtoMapper);
    }

    @Transactional
    public UserResponseDto createUser(UserCreateDto userCreateDto) {
        Set<Role> roles = computeRoles(userCreateDto.roles());

        User user = userCreateDtoToUserMapper.apply(userCreateDto);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(userCreateDto.password()));

        User userSaved = userRepository.save(user);
        return userToUserResponseDtoMapper.apply(userSaved);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserUpdateDto userUpdateDto) {
        User user = retrieveUser(id);
        Set<Role> roles = computeRoles(userUpdateDto.roles());

        user.setRoles(roles);

        User userSaved = userRepository.save(user);
        return userToUserResponseDtoMapper.apply(userSaved);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private Set<Role> computeRoles(Collection<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return new HashSet<>();
        }

        return roleRepository.findAllByIdIn(new HashSet<>(roleIds));
    }

    private User retrieveUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User '" + id + "' not found."));
    }

}
