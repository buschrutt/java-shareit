package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class LastOrNextBookingDtoMapper {

    public static LastOrNextBookingDto addBookingToDto(Booking booking, User user, Item item) {
        return LastOrNextBookingDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .bookerId(user.getId())
                .item(item)
                .status(booking.getStatus())
                .build();

    }

}
