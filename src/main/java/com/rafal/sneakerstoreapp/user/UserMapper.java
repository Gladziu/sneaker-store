package com.rafal.sneakerstoreapp.user;

import com.rafal.sneakerstoreapp.user.model.User;

class UserMapper {

    public static UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
