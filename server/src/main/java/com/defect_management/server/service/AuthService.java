    // @Value("${jwt.secret}")
    // private String SECRET_KEY;
    // @Value("${jwt.expiration}")
    // private long EXPIRATION_TIME;
package com.defect_management.server.service;

import com.defect_management.server.dto.JwtResponse;
import com.defect_management.server.dto.LoginRequest;
import com.defect_management.server.dto.RegisterRequest;
import com.defect_management.server.entity.User;
import com.defect_management.server.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final String SECRET_KEY = "your-secret-key-32-chars-long-at-least"; // Замените на безопасный ключ
    private final long EXPIRATION_TIME = 86400000; // 24 часа

    public JwtResponse register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPasswordHash(new BCryptPasswordEncoder().encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        userRepository.save(user);

        String token = generateToken(user.getUsername(), user.getRole().toString());
        return new JwtResponse(token);
    }

    public JwtResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String token = generateToken(user.getUsername(), user.getRole().toString());
        return new JwtResponse(token);
    }

    private String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPasswordHash())
                .roles(user.getRole().toString())
                .build();
    }
}