package com.perevist.DeliciousFoodApp.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDtoResponse {
    private Long id;
    private String username;
    private String email;
}
