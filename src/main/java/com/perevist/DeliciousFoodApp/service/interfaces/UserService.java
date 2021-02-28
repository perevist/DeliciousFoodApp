package com.perevist.DeliciousFoodApp.service.interfaces;

import com.perevist.DeliciousFoodApp.response.UserDtoResponse;

import java.util.List;

public interface UserService {
    List<UserDtoResponse> getUsers(int page);

    void deleteUser(Long id);
}
