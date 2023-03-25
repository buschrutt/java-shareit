package ru.practicum.shareit.item.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    ItemDto addItem(Item item, Integer ownerId) throws ValidationException, NotFoundException;

    ItemDto updateItem(Integer itemId, Item item, Integer ownerId) throws ValidationException, NotFoundException;

    List<ItemDto> getAllUserItems(Integer ownerId);

    ItemDto getItemById(Integer itemId, Integer ownerId) throws NotFoundException;

    List<ItemDto> getItemsSearched(String text);

    CommentDto addComment(Comment comment, Integer userId, Integer itemId);

}
