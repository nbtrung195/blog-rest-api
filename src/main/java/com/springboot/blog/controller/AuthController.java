package com.springboot.blog.controller;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.payload.JWTAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.SignUpDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.Collections;

@Api(value = "Auth controller exposes sign-in and sign-up REST APIs")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider tokenProvider;

    @ApiOperation(value = "REST API to login or sign-in user to blog app")
    @PostMapping("/sign-in")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    @ApiOperation(value = "REST API to register or sign-up user to blog app")
    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpDto signUpDto){
        signUpDto.getUsername();
        if (userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken", HttpStatus.BAD_REQUEST);
        }
        User user= User.builder()
                .name(signUpDto.getName())
                .username(signUpDto.getUsername())
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .build();
        Role role = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
