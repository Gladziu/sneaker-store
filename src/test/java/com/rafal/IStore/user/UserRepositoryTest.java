package com.rafal.IStore.user;

import com.rafal.IStore.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveUserAndFindByEmail_ExistingUser_ReturnUser() {
        //given
        String email = "bob@example.com";
        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("bob")
                .email(email)
                .build();
        userRepository.save(user);

        User user2 = User.builder()
                .id(UUID.randomUUID())
                .firstName("john")
                .email("john@example.com")
                .build();
        userRepository.save(user2);

        //when
        User result = userRepository.findByEmail(email);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("bob@example.com");
    }

    @Test
    void findByEmail_NonExistingUser() {
        //given
        String email = "james@example.com";

        //when
        User result = userRepository.findByEmail(email);

        //then
        assertThat(result).isNull();
    }

}