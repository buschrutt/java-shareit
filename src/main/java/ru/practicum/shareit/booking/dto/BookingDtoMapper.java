package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Booking;

public class BookingDtoMapper {

    public static Booking toBooking(BookingRequestDto bookingRequestDto) {
        return Booking.builder()
                .itemId(bookingRequestDto.getItemId())
                .start(bookingRequestDto.getStart())
                .end(bookingRequestDto.getEnd())
                .build();
    }

    public static BookingDto toBookingDto(Booking booking) {
        return BookingDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .status(booking.getStatus())
                .build();

    }

}

