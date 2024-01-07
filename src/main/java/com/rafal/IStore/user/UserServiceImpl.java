package com.rafal.IStore.user;

import com.rafal.IStore.user.model.Role;
import com.rafal.IStore.user.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class UserServiceImpl implements UserService {
    private final static String userRole = "ROLE_USER";
    private final static String adminRole = "ROLE_ADMIN";
    private final static String adminEmail = "admin@admin.com";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUserBasedOnRole(UserReceiverDto userReceiverDto) {
        Role role = determineRole(userReceiverDto);
        User user = createUserOrAdmin(userReceiverDto, role);
        userRepository.save(user);
    }

    private Role determineRole(UserReceiverDto userReceiverDto) {
        if (userReceiverDto.getEmail().equals(adminEmail)) {
            return getOrCreateRole(adminRole);
        }
        return getOrCreateRole(userRole);
    }

    private Role getOrCreateRole(String roleName) {
        Role existingRole = roleRepository.findByName(roleName);
        if (existingRole != null) {
            return existingRole;
        }
        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }

    private User createUserOrAdmin(UserReceiverDto userReceiverDto, Role role) {
        return User.builder()
                .firstName(userReceiverDto.getFirstName())
                .lastName(userReceiverDto.getLastName())
                .email(userReceiverDto.getEmail())
                .password(passwordEncoder.encode(userReceiverDto.getPassword()))
                .role(role)
                .build();
    }

    @Override
    public UserDto getCurrentUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
        User user = findUserByEmail(userEmail);
        return UserMapper.mapToDto(user);
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasUserExists(UserReceiverDto userReceiverDto) {
        User existingUser = findUserByEmail(userReceiverDto.getEmail());
        return existingUser != null;
    }

    @Override
    public boolean isAdmin(Authentication authentication) {
        UserDto currentUser = getCurrentUser(authentication);
        User user = findUserByEmail(currentUser.getEmail());
        return isUserRoleAdmin(user);
    }

    private boolean isUserRoleAdmin(User user) {
        return user.getRole().getName().equals("ROLE_ADMIN");
    }

}