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

// http://localhost:8080/hotel-booking/swagger-ui/index.html#/controller/saveUser
@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final AuthenticationService authenticationService;

    @PutMapping("/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody RegisterRequest request, Principal principal) {
        if (!principal.getName().equals(request.getEmail())) {
            MessageDto messageDto = MessageDto.builder()
                    .code("100002")
                    .message("You are not authorized to update this profile")
                    .build();
            ResponseMsgDto responseMsgDto = ResponseMsgDto.builder()
                    .exception("Unauthorized Error")
                    .messages(List.of(messageDto))
                    .build();
            throw new AuthFailureException(HttpStatus.UNAUTHORIZED, responseMsgDto);
        }
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
        return ResponseEntity.ok(authenticationService.update(request));
    }

    @ExceptionHandler(value = {AuthFailureException.class})
    @ResponseBody
    public ResponseEntity<Object> handleLoginFailure(AuthFailureException ex) {
        ResponseMsgDto responseMsgDto = ex.getResponseMsgDto();
        if (ex.getResponseMsgDto().getException().startsWith("Unauthorized")) {
            logger.warn("Authorization failed: Error - {}", ex.getError());
        } else {
            logger.warn("Login/Register failed: Error - {}", ex.getError());
        }
        return ResponseEntity.status(ex.getStatus().value())
                .header("produces", MediaType.APPLICATION_JSON_VALUE)
                .body(responseMsgDto);
    }
}
