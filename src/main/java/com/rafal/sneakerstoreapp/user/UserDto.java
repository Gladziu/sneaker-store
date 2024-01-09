package com.rafal.sneakerstoreapp.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}
