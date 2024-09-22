package com.diary.auth.dto;

import lombok.Getter;

@Getter
public class CreateUserDto {
    private String id;
    private String username;
    private String password;
}
