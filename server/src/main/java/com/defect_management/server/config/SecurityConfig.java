// package com.defect_management.server.config;

// import com.defect_management.server.service.AuthService;
// import io.jsonwebtoken.JwtParser;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.security.Keys;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.CorsConfigurationSource;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// import java.security.Key;
// import java.util.Arrays;

// @Configuration
// public class SecurityConfig {

//     @Autowired
//     private AuthService authService;

//     private final String SECRET_KEY = "zX9kPqW2mL5jN8vR3tY7uQ1wE4rT6yU8iO2pA9sD"; // Безопасный ключ ≥32 символов

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//                 .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                 .csrf(csrf -> csrf.disable())
//                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                 .authorizeHttpRequests(auth -> auth
//                         .requestMatchers("/api/auth/**").permitAll()
//                         .anyRequest().authenticated()
//                 )
//                 .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//         return http.build();
//     }

//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//         return authenticationConfiguration.getAuthenticationManager();
//     }

//     @Bean
//     public JwtAuthenticationFilter jwtAuthenticationFilter() {
//         return new JwtAuthenticationFilter(authService, jwtParser());
//     }

//     @Bean
//     public JwtParser jwtParser() {
//         Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
//         return Jwts.parser().setSigningKey(key);
//     }

//     @Bean
//     public CorsConfigurationSource corsConfigurationSource() {
//         CorsConfiguration configuration = new CorsConfiguration();
//         configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
//         configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//         configuration.setAllowedHeaders(Arrays.asList("*"));
//         configuration.setAllowCredentials(true);
//         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", configuration);
//         return source;
//     }
// }