package ru.practicum.shareit.user;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class UserServiceTests {

    @Autowired
    UserRepository userRepository;

    User userOk = User.builder().email("user@mail.ru").name("User_Name").build();
    User updatedUserOk = User.builder().email("user@mail.ru").name("UpdatedUser_Name").build();
    UserDto userDtoOk = UserDto.builder().id(1).email("user@mail.ru").name("User_Name").build();
    UserDto updatedUserDtoOk = UserDto.builder().id(1).email("user@mail.ru").name("UpdatedUser_Name").build();

    @Test
    @SneakyThrows
    void addUserUnitTest() {
        UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
        Mockito.when(mockUserRepository.save(Mockito.any())).thenReturn(User.builder().id(1).email("user@mail.ru").name("User_Name").build());
        UserServiceImpl.emailValidation(userOk.getEmail());
        assertEquals(UserDtoMapper.toUserDto(mockUserRepository.save(userOk)).toString(), userDtoOk.toString());
    }

    @Test
    @SneakyThrows
    void updateUserUnitTest() {
        UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
        Mockito.when(mockUserRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(updatedUserOk));
        User newUser;
        if (mockUserRepository.findById(1).isPresent()) {
            newUser = mockUserRepository.findById(1).get();
        } else {
            throw new NotFoundException("getUserById: No User Found--");
        }
        if (newUser.getEmail() != null && !Objects.equals(userOk.getEmail(), newUser.getEmail())) {
            UserServiceImpl.emailValidation(updatedUserOk.getEmail());
            newUser.setEmail(updatedUserOk.getEmail());
        }
        if (updatedUserOk.getName() != null) {
            newUser.setName(updatedUserOk.getName());
        }
        Mockito.when(mockUserRepository.save(Mockito.any())).thenReturn(User.builder().id(1).email("user@mail.ru").name("UpdatedUser_Name").build());
        assertEquals(UserDtoMapper.toUserDto(mockUserRepository.save(newUser)).toString(), updatedUserDtoOk.toString());
    }

    @Test
    @SneakyThrows
    void findAllUsersTest() {
        userRepository.save(User.builder().name("firstUser").email("firstUser@ya.ru").build());
        //userService.addUser(userOk);
        //userService.addUser(updatedUserOk);
        //userService.findAllUsers();
    }

}