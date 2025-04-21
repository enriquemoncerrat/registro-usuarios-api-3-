package com.example.registroapi.controller;

import com.example.registroapi.dto.UserRequestDTO;
import com.example.registroapi.entity.User;
import com.example.registroapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody UserRequestDTO userRequest) {
        return ResponseEntity.status(201).body(userService.registerUser(userRequest));
    }
}
