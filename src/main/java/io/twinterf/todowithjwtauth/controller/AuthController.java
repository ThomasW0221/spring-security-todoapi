package io.twinterf.todowithjwtauth.controller;

import io.twinterf.todowithjwtauth.models.UserDao;
import io.twinterf.todowithjwtauth.models.UserDto;
import io.twinterf.todowithjwtauth.models.responses.JwtResponse;
import io.twinterf.todowithjwtauth.service.TodoUserDetailsService;
import io.twinterf.todowithjwtauth.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private TodoUserDetailsService todoUserDetailsService;

    private AuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;

    public AuthController(TodoUserDetailsService todoUserDetailsService,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.todoUserDetailsService = todoUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserDto user) {
        var savedUser = todoUserDetailsService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody UserDto user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch(BadCredentialsException bce) {
            return ResponseEntity.status(403).build();
        }

        UserDetails userDetails = todoUserDetailsService.loadUserByUsername(user.getUsername());

        String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}
