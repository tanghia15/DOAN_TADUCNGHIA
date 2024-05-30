package com.example.nghiahaui.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.example.nghiahaui.entity.User;
import com.example.nghiahaui.repository.UserRepository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.nghiahaui.model.request.CreateUserRequest;
import com.example.nghiahaui.model.request.LoginRequest;
import com.example.nghiahaui.model.response.MessageResponse;
import com.example.nghiahaui.model.response.UserInfoResponse;
import com.example.nghiahaui.security.jwt.JwtUtils;
import com.example.nghiahaui.security.service.UserDetailsImpl;
import com.example.nghiahaui.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*",maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/login")
    @Operation(summary="Đăng nhập")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        // Load user by username to check if enabled
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + loginRequest.getUsername()));

        // Check if the user is enabled
        if (!user.isEnabled()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User account is disabled.");
        }
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
        // return ResponseEntity.ok(jwtCookie);
    }

    @PostMapping("/register")
    @Operation(summary="Đăng ký")
    public ResponseEntity<?> register(@Valid @RequestBody CreateUserRequest request){
      
        userService.register(request);
      
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/logout")
    @Operation(summary="Đăng xuất")
    public ResponseEntity<?> logoutUser() {
      ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
      return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
          .body(new MessageResponse("You've been logout!"));
    }
}
