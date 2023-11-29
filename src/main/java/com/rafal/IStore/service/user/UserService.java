package com.rafal.IStore.service.user;

import com.rafal.IStore.dto.UserDto;
import com.rafal.IStore.model.user.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User getCurrentUser(Authentication authentication);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();

    boolean roleMatching(String roleName, String email);

    boolean isAdmin(Authentication authentication);

    boolean hasUserExists(UserDto userDto);
}