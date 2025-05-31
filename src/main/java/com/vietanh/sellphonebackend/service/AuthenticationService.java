package com.vietanh.sellphonebackend.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.vietanh.sellphonebackend.dto.AuthenticationRequest;
import com.vietanh.sellphonebackend.dto.RegisterRequest;
import com.vietanh.sellphonebackend.dto.AuthenticationResponse;
import com.vietanh.sellphonebackend.entity.User;
import com.vietanh.sellphonebackend.enums.Role;
import com.vietanh.sellphonebackend.exception.AppException;
import com.vietanh.sellphonebackend.exception.ErrorCode;
import com.vietanh.sellphonebackend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    private String SECRET_KEY = "0keZId9EllPZFXRXq3GrjTqWCY66sYDwbB0VKT24XcsJq7bWJYg53gjEUUWotOyOZ3tDbyYbFqnnnCLJRv6Q2oGbcKB2ZzbzOHWIAGgR1tS3XrCtVaMn2NzIx2otO9uhVa1Ibxg9MHppGWRKFH3cYJW3JExiJs43umHZV2ZW4rvdzDhLYb10IiCfHlaqWcYnOMlh0RDCwciTpx2qdKpiVx6jxpGW81xWii4ywhr8RXGsksYqALtX4j7uHpFlBXjT";

    private long ACCESS_DURATION = 7200L;

    public int getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        Jwt jwt = (Jwt) authentication.getPrincipal();
        return Integer.parseInt(jwt.getSubject());
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String accessToken = generateToken(user);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .isAuthenticated(true)
                .build();
    }

    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }


    public String generateToken(User user) {
        try {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(String.valueOf(user.getId()))
                    .issuer("sellphone-backend.com")
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plus(ACCESS_DURATION, ChronoUnit.SECONDS)))
                    .jwtID(UUID.randomUUID().toString())
                    .claim("email", user.getEmail())
                    .claim("scope", "ROLE_" + user.getRole())
                    .build();

            JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
            Payload payload = new Payload(claimsSet.toJSONObject());

            JWSObject jwsObject = new JWSObject(header, payload);
            jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));

            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Error generating token", e);
            throw new RuntimeException("Token generation failed", e);
        }
    }


}
