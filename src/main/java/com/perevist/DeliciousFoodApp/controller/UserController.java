package com.perevist.DeliciousFoodApp.controller;

import com.perevist.DeliciousFoodApp.response.UserDtoResponse;
import com.perevist.DeliciousFoodApp.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    List<UserDtoResponse> getUsers(@RequestParam(required = false) Integer page) {
        int pageNumber = (page != null && page >= 1) ? page - 1 : 0;
        return userService.getUsers(pageNumber);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
