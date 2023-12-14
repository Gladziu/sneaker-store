package com.rafal.IStore.service.user;

import com.rafal.IStore.dto.UserDto;
import com.rafal.IStore.model.user.Role;
import com.rafal.IStore.model.user.User;
import com.rafal.IStore.repository.RoleRepository;
import com.rafal.IStore.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role role = roleRepository.findByName("ROLE_USER");
        user.setRole(List.of(role));
        userRepository.save(user);
    }

    @Override
    public User getCurrentUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
        return findUserByEmail(userEmail);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean isAdmin(Authentication authentication) {
        User user = getCurrentUser(authentication);
        return roleMatching("ROLE_ADMIN", user);
    }

    @Override
    public boolean roleMatching(String roleName, User user) {
        return user.getRole().stream().anyMatch(role -> role.getName().equals(roleName));
    }

    @Override
    public boolean hasUserExists(UserDto userDto) {
        User existingUser = findUserByEmail(userDto.getEmail());
        return existingUser != null;
    }

    private Role addNewRole(String roleName) {
        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }
}