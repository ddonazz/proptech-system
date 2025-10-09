package com.andrea.proptech.user.user.service;

import com.andrea.proptech.core.security.web.dto.UserDetailsResponse;
import com.andrea.proptech.user.exception.ResourceNotFoundException;
import com.andrea.proptech.user.user.data.User;
import com.andrea.proptech.user.user.data.UserRepository;
import com.andrea.proptech.user.user.mapper.UserToUserDetailsResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InternalUserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserToUserDetailsResponseMapper userToUserDetailsResponseMapper;

    @Transactional(readOnly = true)
    public UserDetailsResponse getUserByUsername(String username) {
        User user = retrieveUser(username);
        return userToUserDetailsResponseMapper.apply(user);
    }

    @Transactional(readOnly = true)
    public UserDetailsResponse validateCredentials(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password.");
        }

        return userToUserDetailsResponseMapper.apply(user);
    }

    private User retrieveUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User '" + username + "' not found."));
    }
}
