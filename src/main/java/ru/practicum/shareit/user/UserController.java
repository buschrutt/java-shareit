package ru.practicum.shareit.user;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.error.ConflictException;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;
import ru.practicum.shareit.user.service.UserServiceImpl;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
public class UserController {

    final private UserServiceImpl userService;
    final private UserRepository userRepository;
    final private String epUserId = "/{userId}";

    public UserController(UserServiceImpl userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public UserDto addUser(@RequestBody User user) throws ValidationException, ConflictException {
        return userService.addUser(user, userRepository);
    }

    @PatchMapping(epUserId)
    public UserDto updateUser(@PathVariable int userId, @RequestBody User user) throws ValidationException, ConflictException {
        return userService.updateUser(userId, user, userRepository);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers(userRepository);
    }

    @GetMapping(epUserId)
    public UserDto getUserById(@PathVariable int userId) throws NotFoundException {
        return userService.getUserById(userId, userRepository);
    }

    @DeleteMapping(epUserId)
    public void deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId, userRepository);
    }

}
