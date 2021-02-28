package com.perevist.DeliciousFoodApp.mapper;

import com.perevist.DeliciousFoodApp.model.User;
import com.perevist.DeliciousFoodApp.response.UserDtoResponse;

public class UserMapper {

    private UserMapper() {
    }

    public static UserDtoResponse mapUserToUserDtoResponse(User user) {
        return UserDtoResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
