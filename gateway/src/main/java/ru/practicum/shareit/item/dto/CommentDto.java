package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@PackagePrivate
public class CommentDto {
    Integer id;
    String text;
    Integer item;
    String authorName;
    LocalDateTime created;
}
