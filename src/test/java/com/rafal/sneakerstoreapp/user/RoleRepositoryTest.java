package com.rafal.sneakerstoreapp.user;

import com.rafal.sneakerstoreapp.user.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    public void setUp() {
        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);
    }

    @Test
    void findByName_ExistingRoleName_ReturnsRole() {
        //given
        String name = "ROLE_USER";

        //when
        Role result = roleRepository.findByName(name);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("ROLE_USER");
    }

    @Test
    void findByName_NonExistingRole() {
        //given
        String name = "ROLE_ADMIN";

        //when
        Role result = roleRepository.findByName(name);

        //then
        assertThat(result).isNull();
    }
}