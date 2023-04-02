package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@PackagePrivate
public class UserDto {
    long id;
    String name;
    String email;
}
