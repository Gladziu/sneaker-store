package com.rafal.IStore.repository;

import com.rafal.IStore.model.user.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void testFindByName() {
        //Given

        Role role = new Role();
        role.setName("ROLE_USER");

        roleRepository.save(role);

        //When
        Role expectedRole = roleRepository.findByName("ROLE_USER");

        //Then
        assertNotNull(expectedRole);
    }

    @Test
    void testFindByName_NotFound() {
        //Given

        //When
        Role expectedRole = roleRepository.findByName(null);

        //Then
        assertNull(expectedRole);
    }
}