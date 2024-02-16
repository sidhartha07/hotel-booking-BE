package com.sj.htlbkg.hotelbooking.service;

import com.sj.htlbkg.hotelbooking.config.JwtService;
import com.sj.htlbkg.hotelbooking.controller.AuthController;
import com.sj.htlbkg.hotelbooking.dto.AuthRequest;
import com.sj.htlbkg.hotelbooking.dto.AuthResponse;
import com.sj.htlbkg.hotelbooking.dto.RegisterRequest;
import com.sj.htlbkg.hotelbooking.dto.UserDto;
import com.sj.htlbkg.hotelbooking.model.Role;
import com.sj.htlbkg.hotelbooking.model.User;
import com.sj.htlbkg.hotelbooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .profileImage(request.getProfileImage())
                .createdDateTime(LocalDateTime.now())
                .updatedDateTime(LocalDateTime.now())
                .build();

        userRepository.save(user);
        logger.info("User registered with email: {}", user.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .fullName(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .token(jwtToken)
                .build();
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        logger.info("User logged in with email: {}", user.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .fullName(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .token(jwtToken)
                .build();
    }

    public UserDto update(RegisterRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setAddress(request.getAddress());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setProfileImage(request.getProfileImage());
        user.setUpdatedDateTime(LocalDateTime.now());
        userRepository.save(user);
        logger.info("Details updated for user with email: {}", user.getEmail());
        return UserDto.builder()
                .fullName(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .build();
    }
}
