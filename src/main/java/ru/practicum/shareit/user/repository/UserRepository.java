package ru.practicum.shareit.user.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;
import java.util.HashMap;
import java.util.Map;

//@Data
@Repository
public class UserRepository {
    final static Map<Integer, User> users = new HashMap<>();
    static Integer currentUserId = 0;

    public static Integer RenewCurrentUserId() {
        return ++currentUserId;
    }

    public static Map<Integer, User> getUsers() {
        return users;
    }
}
