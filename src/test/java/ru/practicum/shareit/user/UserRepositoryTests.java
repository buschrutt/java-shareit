package ru.practicum.shareit.user;

import lombok.experimental.PackagePrivate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@PackagePrivate
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
    }

    @AfterEach
    void afterEach() {
        userRepository.deleteAll();
    }

    @Test
    void userRepositoryTest() {
        User user1 = User.builder()
                .name("User1")
                .email("User1@mail.ru")
                .build();
        User user2 = User.builder()
                .name("User2")
                .email("User2@mail.ru")
                .build();
        User user3 = User.builder()
                .name("User3")
                .email("User3@mail.ru")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        List<User> users = userRepository.findAll();
            assertEquals(users.size(), 3);
            assertEquals(users.get(0).getId(), user1.getId());
            assertEquals(users.get(0).getName(), user1.getName());
            assertEquals(users.get(0).getEmail(), user1.getEmail());
            assertEquals(users.get(2).getId(), user3.getId());
            assertEquals(users.get(2).getName(), user3.getName());
            assertEquals(users.get(2).getEmail(), user3.getEmail());
        User user = userRepository.findById(2).get();
            assertEquals(user.getId(), user2.getId());
            assertEquals(user.getName(), user2.getName());
            assertEquals(user.getEmail(), user2.getEmail());
        userRepository.delete(userRepository.findById(1).get());
            assertTrue(userRepository.findById(1).isEmpty());
            assertEquals(userRepository.findAll().size(), 2);
    }

}
