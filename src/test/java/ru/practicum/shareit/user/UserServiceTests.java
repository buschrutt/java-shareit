package ru.practicum.shareit.user;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserServiceTests {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserServiceImpl userServiceImpl;
    private final UserDto userDto = UserDto.builder()
            .id(1)
            .name("John")
            .email("john.doe@mail.com")
            .build();
    private final User user = User.builder()
            .id(1)
            .name("John")
            .email("john.doe@mail.com")
            .build();

    @Test
    @SneakyThrows
    void addUserUnitTest() {
        when(userRepository.save(any())).thenReturn(user);
        assertEquals(userServiceImpl.addUser(userDto).toString(), userDto.toString());
    }

    @Test
    @SneakyThrows
    void updateUserUnitTest() {
        when(userRepository.save(any())).thenReturn(user);
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        assertEquals(userServiceImpl.updateUser(1, userDto).toString(), userDto.toString());
    }

    @Test
    @SneakyThrows
    void findAllUsersTest() {
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);
        assertEquals(userServiceImpl.findAllUsers().get(0).toString(), userDto.toString());
    }

    @Test
    @SneakyThrows
    void findUserByIdTest() {
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        assertEquals(userServiceImpl.findUserById(1).toString(), userDto.toString());
    }

    @Test
    @SneakyThrows
    void deleteTest() {
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        userServiceImpl.deleteUser(1);
    }

}