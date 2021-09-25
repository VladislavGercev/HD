package com.gercev.controller;

import com.gercev.converter.UserConverter;
import com.gercev.domain.User;
import com.gercev.dto.UserDto;
import com.gercev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;

    @PostMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        Optional<User> userOptional = userService.getByEmail(principal.getName());
        return userOptional
                .map(user -> ResponseEntity.ok(userConverter.convert(user)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
