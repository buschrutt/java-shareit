package ru.practicum.shareit.user.service;

import lombok.Data;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.error.ConflictException;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoMapper;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
@Repository
public class UserService implements UserServiceIfc {
    @Override
    public UserDto addUser(User user, UserRepository userRepository) throws ValidationException, ConflictException {
        emailValidation(user.getEmail());
        int currentId = UserRepository.RenewCurrentUserId();
        user.setId(currentId);
        UserRepository.getUsers().put(currentId, user);
        return UserDtoMapper.toUserDto(UserRepository.getUsers().get(currentId));
    }

    @Override
    public UserDto updateUser(int userId, User user, UserRepository userRepository) throws ValidationException, ConflictException {
        if (user.getEmail() != null && !Objects.equals(user.getEmail(), UserRepository.getUsers().get(userId).getEmail())){
            emailValidation(user.getEmail());
            UserRepository.getUsers().get(userId).setEmail(user.getEmail());
        }
        if (user.getName() != null){
            UserRepository.getUsers().get(userId).setName(user.getName());
        }
        return UserDtoMapper.toUserDto(UserRepository.getUsers().get(userId));
    }

    @Override
    public List<UserDto> getAllUsers(UserRepository userRepository) {
        List<UserDto> _return = new ArrayList<>();
        for (Map.Entry<Integer, User> entry : UserRepository.getUsers().entrySet()){
            _return.add(UserDtoMapper.toUserDto(entry.getValue()));
        }
        return _return;
    }

    @Override
    public UserDto getUserById(int userId, UserRepository userRepository) throws NotFoundException {
        if (!UserRepository.getUsers().containsKey(userId)){
            throw new NotFoundException("getUserById: No User Found--");
        }
        return UserDtoMapper.toUserDto(UserRepository.getUsers().get(userId));
    }

    @Override
    public void deleteUser(int userId, UserRepository userRepository) {
        UserRepository.getUsers().remove(userId);
    }

    // %%%%%%%%%% %%%%%%%%%% supporting methods %%%%%%%%%% %%%%%%%%%%

    public void emailValidation(String email) throws ValidationException, ConflictException {
        if (email == null) {
            throw new ValidationException("userValidation: Email Is Empty--");
        }
        for (Map.Entry<Integer, User> entry : UserRepository.getUsers().entrySet()) {
            if (Objects.equals(entry.getValue().getEmail(), email)){
                throw new ConflictException("userValidation: Email Is Not Uniq--");
            }
        }
        String ePattern = "^[a-zA-Z\\d.!#$%&'*+/=?^_`{|}~-]+@((\\[\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}])|(([a-zA-Z\\-\\d]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        if (!m.matches()){
            throw new ValidationException("userValidation: Email Validation Error--");
        }
    }

}
