package com.vietanh.sellphonebackend.entity;

import com.vietanh.sellphonebackend.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int  id;

    String email;
    String password;
    String fullName;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    Role role = Role.USER;
}

