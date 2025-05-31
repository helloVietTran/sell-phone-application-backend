package com.vietanh.sellphonebackend.mapper;

import com.vietanh.sellphonebackend.dto.UserResponse;
import com.vietanh.sellphonebackend.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(User user);
}
