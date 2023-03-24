package ru.practicum.shareit.booking.service;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;

import java.util.List;

public interface BookingService {

    BookingDto addBooking(BookingRequestDto request, Integer userId) throws ValidationException, NotFoundException;

    BookingDto BookingApproval(Integer userId, Integer bookingId, Boolean isApproved);

    BookingDto findBookingById(Integer userId, Integer bookingId);

    List<BookingDto> findAllUserBookings(Integer userId, String state, Integer from, Integer size);

    List<BookingDto> findAllUserItemBookings(Integer userId, String state, Integer from, Integer size);

}
