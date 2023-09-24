package com.rafal.IStore.model.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoleTest {
    private Role role;

    @BeforeEach
    public void setUp(){
        role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
        List<User> users = new ArrayList<>();
        users.add(new User());
        role.setUsers(users);
    }

    @Test
    void testGetId() {
        assertEquals(1L, role.getId());
    }

    @Test
    void testGetName() {
        assertEquals("ROLE_USER", role.getName());
    }

    @Test
    void testGetUsers() {
        assertEquals(1, role.getUsers().size());
    }

}