package ru.practicum.shareit.user;

import lombok.experimental.PackagePrivate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@PackagePrivate
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        userRepository.deleteAll();
        userRepository.save(User.builder().name("User1").email("User1@mail.ru").build());
        userRepository.save(User.builder().name("User2").email("User2@mail.ru").build());
        userRepository.save(User.builder().name("User3").email("User3@mail.ru").build());
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    void userRepositoryTest() {
        List<User> users = userRepository.findAll();
            assertEquals(users.size(), 3);
        userRepository.delete(userRepository.findById(1).get());
            assertTrue(userRepository.findById(1).isEmpty());
            assertEquals(userRepository.findAll().size(), 2);
    }

}
