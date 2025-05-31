package com.vietanh.sellphonebackend.service;

import com.vietanh.sellphonebackend.dto.UserResponse;
import com.vietanh.sellphonebackend.entity.User;
import com.vietanh.sellphonebackend.exception.AppException;
import com.vietanh.sellphonebackend.exception.ErrorCode;
import com.vietanh.sellphonebackend.mapper.UserMapper;
import com.vietanh.sellphonebackend.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;


    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        int userId = Integer.parseInt(context.getAuthentication().getName());

        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toUserResponse(user);
    }
}
