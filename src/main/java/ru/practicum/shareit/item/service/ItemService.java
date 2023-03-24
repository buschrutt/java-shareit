package ru.practicum.shareit.item.service;

import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    ItemDto addItem(Item item, Integer ownerId) throws ValidationException, NotFoundException;

    ItemDto updateItem(Integer itemId, Item item, Integer ownerId) throws ValidationException, NotFoundException;

    List<ItemDto> getAllUserItems(Integer ownerId);

    ItemDto getItemById(Integer itemId) throws NotFoundException;

    List<ItemDto> getItemsSearched(String text);

}
