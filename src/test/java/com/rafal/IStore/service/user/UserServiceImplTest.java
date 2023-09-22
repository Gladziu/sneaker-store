package com.rafal.IStore.service.user;

import com.rafal.IStore.dto.UserDto;
import com.rafal.IStore.model.user.Role;
import com.rafal.IStore.model.user.User;
import com.rafal.IStore.repository.RoleRepository;
import com.rafal.IStore.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void testSaveUser() {
        //Given
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setFirstName("Jan");
        userDto.setLastName("Kowalski");
        userDto.setPassword("password");

        Role role = new Role();
        role.setName("ROLE_USER");

        when(roleRepository.findByName("ROLE_USER")).thenReturn(role);
        when(passwordEncoder.encode("password")).thenReturn("hashedPassword");

        //When
        userService.saveUser(userDto);

        //Then
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(any(User.class));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals("Jan Kowalski", savedUser.getName());
        assertEquals("ROLE_USER", savedUser.getRoles().get(0).getName());
    }

    @Test
    void testCheckRoleExist(){
        // Given
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setFirstName("Jan");
        userDto.setLastName("Kowalski");
        userDto.setPassword("password");

        when(roleRepository.findByName("ROLE_USER")).thenReturn(null);

        Role savedRole = new Role();
        savedRole.setName("ROLE_USER");

        when(roleRepository.save(any(Role.class))).thenReturn(savedRole);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // When
        userService.saveUser(userDto);

        // Then
        verify(userRepository, times(1)).save(any(User.class));
        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    void testGetCurrentUser() {
        //Given
        Authentication authentication = Mockito.mock(Authentication.class);
        UserDetails userDetails = Mockito.mock(UserDetails.class);

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("test@example.com");

        User user = new User();
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        //When
        User currentUser = userService.getCurrentUser(authentication);

        //Then
        assertEquals("test@example.com", currentUser.getEmail());
    }

    @Test
    void testFindUserByEmail() {
        //Given
        User user = new User();
        user.setName("Jan Kowalski");
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        //When
        User userFoundByEmail = userService.findUserByEmail("test@example.com");

        //Then
        assertEquals("Jan Kowalski", userFoundByEmail.getName());
    }


    @Test
    void testFindAllUsers() {
        //Given
        User user = new User();
        user.setName("Jan Kowalski");
        user.setEmail("test@example.com");

        User user2 = new User();
        user2.setName("Will Smith");
        user2.setEmail("will@smith.com");

        List<User> userList = Arrays.asList(user, user2);
        when(userRepository.findAll()).thenReturn(userList);

        //When
        List<UserDto> allUsersDtoList = userService.findAllUsers();

        //Then
        assertEquals(2, allUsersDtoList.size());
        assertEquals("test@example.com", allUsersDtoList.get(0).getEmail());
        assertEquals("will@smith.com", allUsersDtoList.get(1).getEmail());
    }

    @Test
    void testFindAllUserWhenNoUsers(){
        //Given
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        //When
        List<UserDto> allUsersDtoList = userService.findAllUsers();

        //Then
        assertEquals(0, allUsersDtoList.size());
    }

    @Test
    void testRoleMatching() {
        //Given
        Role role = new Role();
        role.setName("ROLE_USER");

        User user = new User();
        user.setName("Jan Kowalski");
        user.setEmail("test@example.com");
        user.setRoles(Collections.singletonList(role));

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        //When
        boolean result = userService.roleMatching("ROLE_USER", "test@example.com");

        //Then
        assertTrue(result);
    }

    @Test
    void testRoleMatchingWhenNoMatchingRole(){
        //Given
        Role role = new Role();
        role.setName("ROLE_ADMIN");

        User user = new User();
        user.setName("Jan Kowalski");
        user.setEmail("test@example.com");
        user.setRoles(Collections.singletonList(role));

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        //When
        boolean result = userService.roleMatching("ROLE_USER", "test@example.com");

        //Then
        assertFalse(result);
    }
}