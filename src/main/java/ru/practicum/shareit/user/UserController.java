package ru.practicum.shareit.user;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.error.ConflictException;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
public class UserController {

    final UserService userService;
    final String epUserId = "/{userId}";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) throws ValidationException, ConflictException {
        return userService.addUser(userDto);
    }

    @PatchMapping(epUserId)
    public UserDto updateUser(@PathVariable Integer userId, @RequestBody UserDto userDto) throws ValidationException, NotFoundException, ConflictException {
        return userService.updateUser(userId, userDto);
    }

    @GetMapping
    public List<UserDto> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping(epUserId)
    public UserDto findUserById(@PathVariable int userId) throws NotFoundException {
        return userService.findUserById(userId);
    }

    @DeleteMapping(epUserId)
    public void deleteUser(@PathVariable int userId) throws NotFoundException {
        userService.deleteUser(userId);
    }

}
