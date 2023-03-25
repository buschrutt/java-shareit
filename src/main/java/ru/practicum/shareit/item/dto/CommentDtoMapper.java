package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.user.repository.UserRepository;


@RequiredArgsConstructor
public class CommentDtoMapper {

    public static CommentDto toCommentDto(Comment comment, UserRepository userRepository) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .item(comment.getItem())
                .authorName(userRepository.findById(comment.getAuthorName()).get().getName())
                .created(comment.getCreated())
                .build();
    }

}
