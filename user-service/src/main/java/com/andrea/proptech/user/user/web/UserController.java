package com.andrea.proptech.user.user.web;

import com.andrea.proptech.user.user.service.UserService;
import com.andrea.proptech.user.user.web.dto.UserDto;
import com.andrea.proptech.user.validation.OnCreate;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Validated({OnCreate.class}) @RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userDto.id())
                .toUri();

        return ResponseEntity.created(location).body(createdUser);
    }
}
