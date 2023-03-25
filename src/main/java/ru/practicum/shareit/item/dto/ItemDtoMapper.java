package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class ItemDtoMapper {

    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .isAvailable(item.getAvailable())
                .build();
    }

    public static ItemDto toItemWithBookingsDto(Item item, List<Booking> itemBookings) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .isAvailable(item.getAvailable())
                .lastBooking(findLastBooking(itemBookings))
                .nextBooking(findNextBooking(itemBookings))
                .build();
    }

    private static Booking findLastBooking(List<Booking> itemBookings) {
        /*Booking lastBooking = null;
        for (Booking booking : itemBookings) {
            if (booking.getStart().isBefore(LocalDateTime.now())) {
                if (lastBooking == null) {
                    lastBooking = booking;
                } else if (booking.getStart().isAfter(lastBooking.getStart())) {
                    lastBooking = booking;
                }
            }
        }*/
        return null;
    }

    private static Booking findNextBooking(List<Booking> itemBookings) {
        /*Booking nextBooking = null;
        for (Booking booking : itemBookings) {
            if (booking.getStart().isAfter(LocalDateTime.now()) || booking.getStart().isBefore(nextBooking.getStart())) {
                if (nextBooking == null) {
                    nextBooking = booking;
                }
            }
        }*/
        return null;
    }

}
