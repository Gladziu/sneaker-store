package com.rafal.sneakerstoreapp.user;

import com.rafal.sneakerstoreapp.user.model.Role;
import com.rafal.sneakerstoreapp.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    @Test
    void loadUserByUsername_ExistingUser_ReturnsUserDetails() {
        // Given
        String email = "test@example.com";
        Role role = new Role();
        role.setName("ROLE_USER");
        User user = User.builder()
                .role(role)
                .email(email)
                .password("password")
                .id(UUID.randomUUID())
                .build();
        when(userRepository.findByEmail(email)).thenReturn(user);

        // When
        UserDetails result = userDetailsService.loadUserByUsername(email);

        // Then
        assertNotNull(result);
        assertEquals(email, result.getUsername());
        assertEquals("password", result.getPassword());
        assertTrue(result.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void loadUserByUsername_NonExistingUser_ThrowsUsernameNotFoundException() {
        // Given
        String nonExistingUserEmail = "nonexistent@example.com";
        when(userRepository.findByEmail(nonExistingUserEmail)).thenReturn(null);

        // When
        UsernameNotFoundException exceptionResult = assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(nonExistingUserEmail));

        // Then
        assertEquals("Invalid username or password.", exceptionResult.getMessage());
        verify(userRepository, times(1)).findByEmail(nonExistingUserEmail);
    }
}