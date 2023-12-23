/*
package com.rafal.IStore.service.user;

import com.rafal.IStore.user.CustomUserDetailsService;
import com.rafal.IStore.user.model.Role;
import com.rafal.IStore.user.model.User;
import com.rafal.IStore.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;
    @Test
    void testLoadUserByUsername() {
        //Give
        Role role = new Role();
        role.setName("ROLE_USER");

        User user = new User();
        user.setName("Jan Kowalski");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRoles(Collections.singletonList(role));

        when(userRepository.findByEmail("test@example.com")).thenReturn(user);

        //When
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("test@example.com");

        //Then
        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertNotNull(authorities);
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
    }

    @Test
    void testLoadUserByUsername_NotFound(){
        //Given
        when(userRepository.findByEmail("test@example.com")).thenReturn(null);

        //When & Then
        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("test@example.com");
        });
    }
}*/
