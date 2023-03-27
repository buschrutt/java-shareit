package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class BookingDtoMapper {

    public static Booking addBookingToBooking(BookingRequestDto bookingRequestDto, int userId) {
        return Booking.builder()
                .itemId(bookingRequestDto.getItemId())
                .start(bookingRequestDto.getStart())
                .end(bookingRequestDto.getEnd())
                .booker(userId)
                .status("WAITING")
                .build();
    }

    public static BookingDto addBookingToDto(Booking booking, User user, Item item) {
        return BookingDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .booker(user)
                .item(item)
                .status(booking.getStatus())
                .build();

    }
}

