package com.rafal.sneakerstoreapp.user;

import com.rafal.sneakerstoreapp.user.model.Role;
import com.rafal.sneakerstoreapp.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void saveUserBasedOnRole_shouldSaveUser_ExistingRole() {
        //given
        UserReceiverDto userReceiverDto = UserReceiverDto.builder()
                .firstName("bob")
                .lastName("smith")
                .email("bob@example.com")
                .password("password")
                .build();

        Role role = new Role();
        role.setName("ROLE_USER");

        when(roleRepository.findByName("ROLE_USER")).thenReturn(role);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        //when
        userService.saveUserBasedOnRole(userReceiverDto);

        //then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void saveUserBasedOnRole_shouldSaveUser_NonExistingRole() {
        //given
        UserReceiverDto userReceiverDto = UserReceiverDto.builder()
                .firstName("bob")
                .lastName("smith")
                .email("bob@example.com")
                .password("password")
                .build();

        when(roleRepository.findByName("ROLE_USER")).thenReturn(null);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        //when
        userService.saveUserBasedOnRole(userReceiverDto);

        //then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void saveUserBasedOnRole_shouldSaveAdmin() {
        //given
        UserReceiverDto userReceiverDto = UserReceiverDto.builder()
                .firstName("admin")
                .lastName("admin")
                .email("admin@admin.com")
                .password("password")
                .build();

        Role role = new Role();
        role.setName("ROLE_ADMIN");

        when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(role);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        //when
        userService.saveUserBasedOnRole(userReceiverDto);

        //then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getCurrentUser_ReturnsCurrentUser() {
        //given
        UserDetails userDetails = mock(UserDetails.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("bob@example.com");

        User user = new User();
        user.setEmail("bob@example.com");

        when(userRepository.findByEmail("bob@example.com")).thenReturn(user);

        //when
        UserDto result = userService.getCurrentUser(authentication);

        //then
        assertThat(result).isNotNull();
        assertThat("bob@example.com").isEqualTo(result.getEmail());
    }

    @Test
    void findAllUsers_ReturnsUsersList() {
        //given
        User user1 = new User();
        user1.setEmail("bob@example.com");

        User user2 = new User();
        user2.setEmail("james@example.com");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        //when
        List<UserDto> result = userService.findAllUsers();

        //then
        assertThat(result).isNotEmpty();
        assertThat(result).hasSize(2);
        assertThat(result.get(1).getEmail()).isEqualTo("james@example.com");
    }

    @Test
    void findAllUsers_NonExistingUsers() {
        //given
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        //when
        List<UserDto> result = userService.findAllUsers();

        //then
        assertThat(result).isEmpty();
    }

    @Test
    void hasUserExists_ExistingUser_ReturnsTrue() {
        //given
        UserReceiverDto userReceiverDto = new UserReceiverDto();
        userReceiverDto.setEmail("bob@example.com");

        User existingUser = new User();
        existingUser.setEmail("bob@example.com");

        when(userRepository.findByEmail("bob@example.com")).thenReturn(existingUser);

        //when
        boolean result = userService.hasUserExists(userReceiverDto);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void hasUserExists_NonExistingUser_ReturnsFalse() {
        //given
        UserReceiverDto userReceiverDto = new UserReceiverDto();
        userReceiverDto.setEmail("bob@example.com");


        when(userRepository.findByEmail("bob@example.com")).thenReturn(null);

        //when
        boolean result = userService.hasUserExists(userReceiverDto);

        //then
        assertThat(result).isFalse();
    }

    @Test
    void isAdmin_ExistingAdmin_ReturnsTrue() {
        //given
        UserDetails userDetails = mock(UserDetails.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("admin@example.com");

        Role role = new Role();
        role.setName("ROLE_ADMIN");

        User user = new User();
        user.setEmail("admin@example.com");
        user.setRole(role);

        when(userRepository.findByEmail("admin@example.com")).thenReturn(user);

        //when
        boolean result = userService.isAdmin(authentication);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void isAdmin_NotAdminRole_ReturnsFalse() {
        //given
        UserDetails userDetails = mock(UserDetails.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("bob@example.com");

        Role role = new Role();
        role.setName("ROLE_USER");

        User user = User.builder()
                .email("bob@example.com")
                .role(role)
                .build();

        when(userRepository.findByEmail("bob@example.com")).thenReturn(user);

        //when
        boolean result = userService.isAdmin(authentication);

        //then
        assertThat(result).isFalse();
    }
}