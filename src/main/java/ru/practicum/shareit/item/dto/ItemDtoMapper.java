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
                .isAvailable(item.getAvailable())
                .requestId(item.getRequestId())
                .build();
    }

    public static ItemDto toItemWithBookingsAndCommentDtos(Item item, List<CommentDto> itemComments, LastOrNextBookingDto nextBookingDto, LastOrNextBookingDto lastBookingDto) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .isAvailable(item.getAvailable())
                .lastBooking(lastBookingDto)
                .nextBooking(nextBookingDto)
                .comments(itemComments)
                .requestId(item.getRequestId())
                .build();
    }
}
