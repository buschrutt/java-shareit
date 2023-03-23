package ru.practicum.shareit.item.service;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class ItemServiceImpl implements ItemService {
    @Override
    public ItemDto addItem(Item item, Integer ownerId, ItemRepository itemRepository) throws ValidationException, NotFoundException {
        item.setOwnerId(ownerId);
        itemValidation(item);
        int currentId = itemRepository.renewCurrentItemId();
        item.setId(currentId);
        itemRepository.getItems().put(currentId, item);
        return ItemDtoMapper.toItemDto(itemRepository.getItems().get(currentId));
    }

    @Override
    public ItemDto updateItem(Integer itemId, Item item, Integer ownerId, ItemRepository itemRepository) throws NotFoundException {
        if (!Objects.equals(itemRepository.getItems().get(itemId).getOwnerId(), ownerId)) {
            throw new NotFoundException("addItem: No User Found--");
        }
        if (item.getName() != null) {
            itemRepository.getItems().get(itemId).setName(item.getName());
        }
        if (item.getDescription() != null) {
            itemRepository.getItems().get(itemId).setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            itemRepository.getItems().get(itemId).setAvailable(item.getAvailable());
        }
        return ItemDtoMapper.toItemDto(itemRepository.getItems().get(itemId));
    }

    @Override
    public List<ItemDto> getAllItems(Integer ownerId, ItemRepository itemRepository) {
        List<ItemDto> _return = new ArrayList<>();
        for (Map.Entry<Integer, Item> entry : itemRepository.getItems().entrySet()){
            if (Objects.equals(entry.getValue().getOwnerId(), ownerId)) {
                _return.add(ItemDtoMapper.toItemDto(entry.getValue()));
            }
        }
        return _return;
    }

    @Override
    public ItemDto getItemById(Integer itemId, ItemRepository itemRepository) {
        return ItemDtoMapper.toItemDto(itemRepository.getItems().get(itemId));
    }

    @Override
    public List<ItemDto> getItemsSearched(String text, ItemRepository itemRepository) {
        List<ItemDto> ItemDtoList = new ArrayList<>();
        if (text.isEmpty()) {
            return ItemDtoList;
        }
        for (Map.Entry<Integer, Item> entry : itemRepository.getItems().entrySet()){
            if (entry.getValue().getAvailable() && (entry.getValue().getName().toLowerCase().contains(text.toLowerCase()) || entry.getValue().getDescription().toLowerCase().contains(text.toLowerCase()))) {
                ItemDtoList.add(ItemDtoMapper.toItemDto(entry.getValue()));
            }
        }
        return ItemDtoList;
    }

    // %%%%%%%%%% %%%%%%%%%% supporting methods %%%%%%%%%% %%%%%%%%%%

    private void itemValidation(Item item) throws ValidationException, NotFoundException {
        if (item.getName().isEmpty() || item.getDescription() == null || item.getAvailable() == null) {
            throw new ValidationException("itemValidation: Name Or Description Is Empty--");
        }
        if (!UserRepository.getUsers().containsKey(item.getOwnerId())) {
            throw new NotFoundException("getUserById: No User Found--");
        }
    }
}
