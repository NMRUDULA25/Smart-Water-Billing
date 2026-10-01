package com.smartwater.smartwaterbilling.controller;

import com.smartwater.smartwaterbilling.dto.auth.LoginRequest;
import com.smartwater.smartwaterbilling.dto.auth.LoginResponse;
import com.smartwater.smartwaterbilling.dto.auth.RegisterRequest;
import com.smartwater.smartwaterbilling.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        userService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        return ResponseEntity.ok(userService.login(request));
    }

}