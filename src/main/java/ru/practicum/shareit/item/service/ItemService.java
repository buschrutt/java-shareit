package ru.practicum.shareit.item.service;

import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.util.List;

public interface ItemService {

    ItemDto addItem(Item item, Integer ownerId, ItemRepository repository) throws ValidationException, NotFoundException;

    ItemDto updateItem(Integer itemId, Item item, Integer ownerId, ItemRepository repository) throws ValidationException, NotFoundException;

    List<ItemDto> getAllItems(Integer ownerId, ItemRepository repository);

    ItemDto getItemById(Integer itemId, ItemRepository repository);

    List<ItemDto> getItemsSearched(String text, ItemRepository repository);

}
