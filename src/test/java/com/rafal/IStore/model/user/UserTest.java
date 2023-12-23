/*
package com.rafal.IStore.model.user;

import com.rafal.IStore.user.model.Role;
import com.rafal.IStore.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setPassword("password");

        Role role = new Role();
        role.setName("ROLE_USER");

        List<Role> roles = new ArrayList<>();
        roles.add(role);

        user.setRoles(roles);
    }

    @Test
    public void testGetName() {
        assertEquals("John Doe", user.getName());
    }

    @Test
    public void testGetEmail() {
        assertEquals("john@example.com", user.getEmail());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password", user.getPassword());
    }

    @Test
    void testGetId(){
        assertEquals(1L, user.getId());
    }

    @Test
    public void testGetRoles() {
        assertEquals(1, user.getRoles().size());
        assertEquals("ROLE_USER", user.getRoles().get(0).getName());
    }
}*/
