package com.saketmungsemajorproject.Airbnb.App.security;

import com.saketmungsemajorproject.Airbnb.App.entity.User;
import com.saketmungsemajorproject.Airbnb.App.repository.UserRepository;
import com.saketmungsemajorproject.Airbnb.App.service.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JWTService jwtService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    //This runs on every single HTTP request before it reaches the controller
    //The Security Gatekeeper
        try {
            //Step 1: Check for Authorization header
            final String requestTokenHeader = request.getHeader("Authorization");
            //Expected: "Bearer eyJhbGci..."
            if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")) {
                // Let the request through WITHOUT authentication
                // Public endpoints (/auth/signup, /auth/login, /hotels/search) will still work
                filterChain.doFilter(request, response);
                return;
            }

            String token = requestTokenHeader.split("Bearer ")[1];
            Long userId = jwtService.getUserIdFromToken(token);

            //Load user from DB and set in SecurityContext
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                                  //No exist authentication
                User user = userService.getUserById(userId);
                // check if the user should be allowed
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                //Now Spring Security Knows who this user is for this request
            }
            filterChain.doFilter(request, response);//Continue to controller
        } catch (JwtException ex) {
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }

    }
}