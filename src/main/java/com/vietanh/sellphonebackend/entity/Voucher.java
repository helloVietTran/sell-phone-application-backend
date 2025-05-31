package com.vietanh.sellphonebackend.entity;

import com.vietanh.sellphonebackend.enums.VoucherCode;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Enumerated(EnumType.STRING)
    VoucherCode code;

    Integer discountPercent;

    Boolean active;

    Instant createdAt;

    Instant expiredAt;
}
