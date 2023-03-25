package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query(
            value = "SELECT * FROM comments WHERE item_id = ? ORDER BY comments.created DESC",
            nativeQuery = true)
    List<Comment> itemComments(Integer itemId);

}