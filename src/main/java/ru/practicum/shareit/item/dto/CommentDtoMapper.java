package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;


@RequiredArgsConstructor
public class CommentDtoMapper {

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .build();
    }

}
