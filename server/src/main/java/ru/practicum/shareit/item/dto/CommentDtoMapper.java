package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.model.Comment;

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

    public static Comment toComment(CommentDto commentDto, Integer authorId) {
        return Comment.builder()
                .text(commentDto.getText())
                .item(commentDto.getItem())
                .authorName(authorId)
                .created(commentDto.getCreated())
                .build();
    }

}
