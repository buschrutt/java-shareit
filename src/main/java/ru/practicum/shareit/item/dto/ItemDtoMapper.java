package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;

public class ItemDtoMapper {
    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .description(item.getDescription())
                        .isAvailable(item.getAvailable())
                        //.ownerId(item.getOwnerId())
                        //.requestLink(item.getRequestLink())
                        .build();
    }
}
