package ru.practicum.shareit.user.service;

import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto addUser(UserDto userDto) throws ValidationException;

    UserDto updateUser(Integer userId, UserDto userDto) throws ValidationException, NotFoundException;

    List<UserDto> findAllUsers();

    UserDto findUserById(int userId) throws NotFoundException;

    void deleteUser(Integer userId) throws NotFoundException;


}
