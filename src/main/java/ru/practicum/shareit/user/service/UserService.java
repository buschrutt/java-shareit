package ru.practicum.shareit.user.service;

import ru.practicum.shareit.error.ConflictException;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

public interface UserService {

    UserDto addUser(User user, UserRepository userRepository) throws ValidationException, ConflictException;

    UserDto updateUser(int userId, User user, UserRepository userRepository) throws ValidationException, ConflictException;

    List<UserDto> getAllUsers(UserRepository userRepository);

    UserDto getUserById(int userId, UserRepository userRepository) throws NotFoundException;

    void deleteUser(int userId, UserRepository userRepository);


}
