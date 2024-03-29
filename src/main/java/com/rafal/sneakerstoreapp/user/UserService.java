package com.rafal.sneakerstoreapp.user;

import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {
    void saveUserBasedOnRole(UserReceiverDto userReceiverDto);

    UserDto getCurrentUser(Authentication authentication);

    List<UserDto> findAllUsers();

    boolean isAdmin(Authentication authentication);

    boolean hasUserExists(UserReceiverDto userReceiverDto);
}