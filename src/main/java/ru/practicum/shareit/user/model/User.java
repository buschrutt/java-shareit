package ru.practicum.shareit.user.model;

import lombok.Data;
import lombok.experimental.PackagePrivate;

/**
 * TODO Sprint add-controllers.
 */

@Data
@PackagePrivate
public class User {
    Integer id;
    String name;
    String email;
}
