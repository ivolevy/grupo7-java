package com.example.uade.tpo.dtos.request;

import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CreateUserRequestDto {
    String name;
    String lastName;
    String email;
    String password;
}
