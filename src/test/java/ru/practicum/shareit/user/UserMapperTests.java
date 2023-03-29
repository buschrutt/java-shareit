package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoMapper;
import ru.practicum.shareit.user.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTests {

    @Test
    void toUserDtoTest() {
        User user = User.builder().id(1).email("user@mail.ru").name("user_Name").build();
        UserDto userDto = UserDto.builder().id(1).email("user@mail.ru").name("user_Name").build();
        assertEquals(UserDtoMapper.toUserDto(user).toString(), userDto.toString());
    }

}
