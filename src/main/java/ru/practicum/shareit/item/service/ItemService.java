package ru.practicum.shareit.item.service;

import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {

    ItemDto addItem(ItemDto itemDto, Integer ownerId) throws ValidationException, NotFoundException;

    ItemDto updateItem(Integer itemId, ItemDto itemDto, Integer ownerId) throws ValidationException, NotFoundException;

    List<ItemDto> getAllUserItems(Integer ownerId);

    ItemDto getItemById(Integer itemId, Integer ownerId) throws NotFoundException;

    List<ItemDto> getItemsSearched(String text);

    CommentDto addComment(CommentDto commentDto, Integer userId, Integer itemId) throws ValidationException;

}
