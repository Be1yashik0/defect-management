package com.defect_management.server.controller;

import com.defect_management.server.config.JwtUtil;
import com.defect_management.server.dto.AuthRequest;
import com.defect_management.server.dto.AuthResponse;
import com.defect_management.server.entity.User;
import com.defect_management.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public User register(@RequestBody AuthRequest authRequest) {
        return userService.registerUser(authRequest.getUsername(), authRequest.getPassword(), null);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        String jwt = jwtUtil.generateToken(authRequest.getUsername(), role);
        AuthResponse response = new AuthResponse();
        response.setToken(jwt);
        return response;
    }
}