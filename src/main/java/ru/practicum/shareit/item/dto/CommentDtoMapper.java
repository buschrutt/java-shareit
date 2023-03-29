package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.model.Comment;

// mapper checkup
@RequiredArgsConstructor
public class CommentDtoMapper {

    public static CommentDto toCommentDto(Comment comment, String authorName) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .item(comment.getItem())
                .authorName(authorName)
                .created(comment.getCreated())
                .build();
    }

}
