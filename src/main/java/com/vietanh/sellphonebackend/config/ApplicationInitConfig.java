package com.vietanh.sellphonebackend.config;

import com.vietanh.sellphonebackend.entity.User;
import com.vietanh.sellphonebackend.enums.Role;
import com.vietanh.sellphonebackend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    static final String ADMIN_EMAIL = "admin123@gmail.com";
    static final String ADMIN_PASSWORD = "admin123";

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        log.info("Initializing application...");

        return args -> {
            if (userRepository.findByEmail(ADMIN_EMAIL).isEmpty()) {

                User adminUser = User.builder()
                        .email(ADMIN_EMAIL)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .fullName("Viet Anh")
                        .role(Role.ADMIN)
                        .build();

                userRepository.save(adminUser);

                log.warn("⚠️ Admin user created with default password '{}'. Please change it!", ADMIN_PASSWORD);
            }
        };
    }
}
