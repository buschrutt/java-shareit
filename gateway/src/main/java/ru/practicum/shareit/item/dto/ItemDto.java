package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@PackagePrivate
public class ItemDto {
    Integer id;
    String name;
    String description;
    Boolean available;
    Integer ownerId;
    Integer requestId;
    List<CommentDto> comments;
}
