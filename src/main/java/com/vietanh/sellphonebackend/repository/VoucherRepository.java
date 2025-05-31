package com.vietanh.sellphonebackend.repository;

import com.vietanh.sellphonebackend.entity.Voucher;
import com.vietanh.sellphonebackend.enums.VoucherCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    Optional<Voucher> findByCodeAndActiveTrue(VoucherCode code);
}
