package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.dto.LastOrNextBookingDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@RequiredArgsConstructor
public class ItemDtoMapper {

    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .requestId(item.getRequestId())
                .build();
    }

    public static ItemDto toItemWithBookingsAndCommentDtos(Item item, List<CommentDto> itemComments, LastOrNextBookingDto nextBookingDto, LastOrNextBookingDto lastBookingDto) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .lastBooking(lastBookingDto)
                .nextBooking(nextBookingDto)
                .comments(itemComments)
                .requestId(item.getRequestId())
                .build();
    }

    public static Item toItem(ItemDto itemDto, Integer ownerId) {
        return Item.builder()
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .requestId(itemDto.getRequestId())
                .ownerId(ownerId)
                .build();
    }

}
