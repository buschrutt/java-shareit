package ru.practicum.shareit.user;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceTests {

    @Test
    void addUserUnitTest0() {
        UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
        Mockito
                .when(mockUserRepository.save(Mockito.any()))
                .thenReturn(User.builder().id(1).email("userOk@mail.ru").name("userOk_Name").build());
        User userOk = User.builder().email("OkUser@mail.ru").name("OkUser_Name").build();
        UserDto userDtoOk = UserDto.builder().id(1).email("userOk@mail.ru").name("userOk_Name").build();
        assertEquals(UserDtoMapper.toUserDto(mockUserRepository.save(userOk)).toString(), userDtoOk.toString());
    }

}