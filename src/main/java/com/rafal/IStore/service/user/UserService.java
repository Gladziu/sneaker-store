package com.rafal.IStore.service.user;

import com.rafal.IStore.dto.UserDto;
import com.rafal.IStore.model.user.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();

    boolean roleMatching(String roleName, String email);
}