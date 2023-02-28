package com.xactip.intellicent.usermanagementservice.controller;

import com.xactip.intellicent.usermanagementservice.dto.UserDto;
import com.xactip.intellicent.usermanagementservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/users/register")
    public ResponseEntity<UserDto> registerCurrentUser(Principal principal, @RequestBody @Valid UserDto user) {
        UserDto registeredUser = userService.registerUser(principal.getName(), user);
        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/users/current")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        UserDto user = userService.getUserById(principal.getName());
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("user_id") String id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}