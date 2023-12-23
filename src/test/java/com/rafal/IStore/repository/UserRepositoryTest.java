/*
package com.rafal.IStore.repository;

import com.rafal.IStore.user.model.Role;
import com.rafal.IStore.user.model.User;
import com.rafal.IStore.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testUserRepository_SaveAll(){
        //Given
        Role role = new Role();
        role.setName("ROLE_USER");

        User user = new User();
        user.setName("Jan Kowalski");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRoles(List.of(role));

        //When
        User savedUser = userRepository.save(user);

        //Then
        assertNotNull(savedUser);
    }

    @Test
    void UserRepository_GetAll(){
        //Given
        Role role = new Role();
        role.setName("ROLE_USER");

        User user = new User();
        user.setName("Jan Kowalski");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRoles(List.of(role));
        userRepository.save(user);

        //When
        List<User> users = userRepository.findAll();

        //Then
        assertNotNull(users);
        assertEquals(1, users.size());
    }
    @Test
    void testFindByEmail() {
        //Given
        Role role = new Role();
        role.setName("ROLE_USER");

        User user = new User();
        user.setName("Jan Kowalski");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRoles(List.of(role));
        userRepository.save(user);

        //When
        User foundUser = userRepository.findByEmail("test@example.com");

        //Then
        assertNotNull(foundUser);
        assertEquals("test@example.com", foundUser.getEmail());
    }

    @Test
    void testFindByEmail_NotFound() {
        //Given
        String email = "nonexistent@example.com";

        //When
        User foundUser = userRepository.findByEmail(email);

        //Then
        assertNull(foundUser);
    }
}
*/
