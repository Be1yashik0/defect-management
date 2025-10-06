// package com.defect_management.server.config;

// import com.defect_management.server.service.AuthService;
// import io.jsonwebtoken.JwtParser;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.web.filter.OncePerRequestFilter;

// import java.io.IOException;

// public class JwtAuthenticationFilter extends OncePerRequestFilter {

//     private final AuthService authService;
//     private final JwtParser jwtParser;

//     public JwtAuthenticationFilter(AuthService authService, JwtParser jwtParser) {
//         this.authService = authService;
//         this.jwtParser = jwtParser;
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//             throws ServletException, IOException {
//         String header = request.getHeader("Authorization");
//         if (header != null && header.startsWith("Bearer ")) {
//             String token = header.substring(7);
//             try {
//                 String username = jwtParser.parseClaimsJws(token).getBody().getSubject();
//                 var authentication = new UsernamePasswordAuthenticationToken(
//                         username, null, authService.loadUserByUsername(username).getAuthorities());
//                 authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                 SecurityContextHolder.getContext().setAuthentication(authentication);
//             } catch (Exception e) {
//                 SecurityContextHolder.clearContext();
//             }
//         }
//         filterChain.doFilter(request, response);
//     }
// }