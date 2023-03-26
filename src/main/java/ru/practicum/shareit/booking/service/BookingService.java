package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;

import java.util.List;

public interface BookingService {

    BookingDto addBooking(BookingRequestDto request, Integer userId) throws ValidationException, NotFoundException;

    BookingDto bookingApproval(Integer userId, Integer bookingId, Boolean isApproved) throws NotFoundException, ValidationException;

    BookingDto findBookingById(Integer userId, Integer bookingId) throws NotFoundException, ValidationException;

    List<BookingDto> findAllUserBookings(Integer userId, String state, Integer from, Integer size) throws NotFoundException, ValidationException;

    List<BookingDto> findAllOwnerBookings(Integer userId, String state, Integer from, Integer size) throws NotFoundException, ValidationException;

}
