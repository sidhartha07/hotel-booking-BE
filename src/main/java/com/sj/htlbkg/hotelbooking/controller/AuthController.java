package com.sj.htlbkg.hotelbooking.controller;

import com.sj.htlbkg.hotelbooking.dto.*;
import com.sj.htlbkg.hotelbooking.exception.AuthFailureException;
import com.sj.htlbkg.hotelbooking.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        if (ObjectUtils.anyNull(request.getEmail(), request.getPassword(), request.getFirstName(),
                request.getLastName(), request.getPhoneNumber(), request.getAddress()) ||
                !StringUtils.hasLength(request.getEmail()) || !StringUtils.hasLength(request.getPassword()) ||
                !StringUtils.hasLength(request.getFirstName()) || !StringUtils.hasLength(request.getLastName()) ||
                !StringUtils.hasLength(request.getPhoneNumber()) || !StringUtils.hasLength(request.getAddress())) {
            MessageDto messageDto = MessageDto.builder()
                    .code("100001")
                    .message("Please provide mandatory fields")
                    .build();
            ResponseMsgDto responseMsgDto = ResponseMsgDto.builder()
                    .exception("Registration Failed with error")
                    .messages(List.of(messageDto))
                    .build();
            throw new AuthFailureException(HttpStatus.BAD_REQUEST, responseMsgDto);
        }
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        if (ObjectUtils.anyNull(request.getEmail(), request.getPassword()) ||
                !StringUtils.hasLength(request.getEmail()) || !StringUtils.hasLength(request.getPassword())) {
            MessageDto messageDto = MessageDto.builder()
                    .code("100001")
                    .message("Please provide mandatory fields")
                    .build();
            ResponseMsgDto responseMsgDto = ResponseMsgDto.builder()
                    .exception("Login Failed with error")
                    .messages(List.of(messageDto))
                    .build();
            throw new AuthFailureException(HttpStatus.BAD_REQUEST, responseMsgDto);
        }
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @ExceptionHandler(value = {AuthFailureException.class})
    @ResponseBody
    public ResponseEntity<Object> handleLoginFailure(AuthFailureException ex) {
        ResponseMsgDto responseMsgDto = ex.getResponseMsgDto();
        logger.warn("Login/Register failed: Error - {}", ex.getError());
        return ResponseEntity.status(ex.getStatus().value())
                .header("produces", MediaType.APPLICATION_JSON_VALUE)
                .body(responseMsgDto);
    }
}
