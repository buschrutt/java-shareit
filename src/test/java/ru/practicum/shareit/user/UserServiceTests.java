package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.practicum.shareit.error.ConflictException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.service.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceTest {

    @Test
    void addUserUnitTest0() throws ValidationException, ConflictException {
        UserService mockUserService = Mockito.mock(UserService.class);
        User userOk = User.builder().email("OkUser@mail.ru").name("OkUser_Name").build();
        UserDto userDtoOk = UserDto.builder().id(1).email("userOk@mail.ru").name("userOk_Name").build();
        Mockito
                .when(mockUserService.addUser(Mockito.any()))
                .thenReturn(UserDto.builder().id(1).email("userOk@mail.ru").name("userOk_Name").build());
        UserDto userDto = mockUserService.addUser(userOk);
        assertEquals(userDto.toString(), userDtoOk.toString());
    }

}