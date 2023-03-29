package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoMapper;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingMapperTests {

    @Test
    void addBookingToBookingTest() {
        LocalDateTime timeStart = LocalDateTime.now();
        LocalDateTime timeEnd = LocalDateTime.now();
        int userId = 1;
        BookingRequestDto bookingRequestDto = BookingRequestDto.builder()
                .itemId(2)
                .start(timeStart)
                .end(timeEnd)
                .build();
        Booking booking = Booking.builder()
                .itemId(2)
                .start(timeStart)
                .end(timeEnd)
                .booker(userId)
                .status("WAITING")
                .build();
        assertEquals(BookingDtoMapper.addBookingToBooking(bookingRequestDto, userId).toString(), booking.toString());
    }

    @Test
    void addBookingToDtoTest() {
        LocalDateTime timeStart = LocalDateTime.now();
        LocalDateTime timeEnd = LocalDateTime.now();
        Booking booking = Booking.builder()
                .id(5)
                .itemId(2)
                .start(timeStart)
                .end(timeEnd)
                .status("WAITING")
                .build();
        Item item = Item.builder()
                .id(1)
                .name("Item_Name")
                .description("Some_Item")
                .available(true)
                .requestId(3)
                .build();
        User user = User.builder()
                .id(1)
                .name("Some_User_Name")
                .email("mail@mail.ru")
                .build();
        BookingDto bookingDto = BookingDto.builder()
                .id(5)
                .start(timeStart)
                .end(timeEnd)
                .item(item)
                .booker(user)
                .status("WAITING")
                .build();
        assertEquals(BookingDtoMapper.addBookingToDto(booking, user, item).toString(), bookingDto.toString());
    }

}
