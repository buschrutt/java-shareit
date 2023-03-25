package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.PackagePrivate;

import java.time.LocalDateTime;

@Data
@Builder
@PackagePrivate
public class CommentDto {
    Integer id;
    String text;
    Integer item;
    String authorName;
    LocalDateTime created;
}
