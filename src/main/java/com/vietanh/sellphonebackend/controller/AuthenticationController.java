package com.vietanh.sellphonebackend.controller;

import com.vietanh.sellphonebackend.dto.AuthenticationRequest;
import com.vietanh.sellphonebackend.dto.RegisterRequest;
import com.vietanh.sellphonebackend.dto.AuthenticationResponse;
import com.vietanh.sellphonebackend.dto.ApiResponse;
import com.vietanh.sellphonebackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.login(request))
                .build();
    }

    @PostMapping("/register")
    public ApiResponse<Void> register(@RequestBody RegisterRequest request) {
        authenticationService.register(request);
        return ApiResponse.<Void>builder()
                .message("Register successfully")
                .build();
    }
}
