package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto addUser(UserDto userDto) throws ValidationException {
        User user = UserDtoMapper.toUser(userDto);
        emailValidation(user.getEmail());
        return UserDtoMapper.toUserDto(userRepository.save(user));
    }

    @Override
    public UserDto updateUser(Integer userId, UserDto userDto) throws ValidationException, NotFoundException {
        User user = UserDtoMapper.toUser(userDto);
        User newUser;
        if (userRepository.findById(userId).isPresent()) {
            newUser = userRepository.findById(userId).get();
        } else {
            throw new NotFoundException("getUserById: No User Found--");
        }
        if (user.getEmail() != null && !Objects.equals(user.getEmail(), newUser.getEmail())) {
            emailValidation(user.getEmail());
            newUser.setEmail(user.getEmail());
        }
        if (user.getName() != null) {
            newUser.setName(user.getName());
        }
        return UserDtoMapper.toUserDto(userRepository.save(newUser));
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<UserDto> userDtoList = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User user : users) {
            userDtoList.add(UserDtoMapper.toUserDto(user));
        }
        return userDtoList;
    }

    @Override
    public UserDto findUserById(int userId) throws NotFoundException {
        if (userRepository.findById(userId).isPresent()) {
            return UserDtoMapper.toUserDto(userRepository.findById(userId).get());
        } else {
            throw new NotFoundException("getUserById: No User Found--");
        }
    }

    @Override
    public void deleteUser(Integer userId) throws NotFoundException {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.delete(userRepository.findById(userId).get());
        } else {
            throw new NotFoundException("getUserById: No User Found--");
        }
    }

    // %%%%%%%%%% %%%%%%%%%% supporting methods %%%%%%%%%% %%%%%%%%%%

    public static void emailValidation(String email) throws ValidationException {
        if (email == null) {
            throw new ValidationException("userValidation: Email Is Empty--");
        }
        String ePattern = "^[a-zA-Z\\d.!#$%&'*+/=?^_`{|}~-]+@((\\[\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}])|(([a-zA-Z\\-\\d]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        if (!m.matches()) {
            throw new ValidationException("userValidation: Email Validation Error--");
        }
    }

}
