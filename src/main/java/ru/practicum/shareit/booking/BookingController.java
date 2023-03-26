package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {

    final BookingService bookingService;
    final String epBookingId = "/{bookingId}";

    final String sharerId = "X-Sharer-User-Id";

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingDto addBooking(@RequestHeader(value = sharerId) Integer userId, @RequestBody BookingRequestDto request) throws ValidationException, NotFoundException {
        return bookingService.addBooking(request, userId);
    }

    @PatchMapping(epBookingId)
    public BookingDto bookingApproval(@RequestHeader(value = sharerId) Integer userId, @PathVariable Integer bookingId, @RequestParam Boolean approved) throws NotFoundException, ValidationException {
        return bookingService.bookingApproval(userId, bookingId, approved);
    }

    @GetMapping(epBookingId)
    public BookingDto findBookingById(@RequestHeader(value = sharerId) Integer userId, @PathVariable Integer bookingId) throws ValidationException, NotFoundException {
        return bookingService.findBookingById(userId, bookingId);
    }

    @GetMapping
    public List<BookingDto> findAllUserBookings(@RequestHeader(value = sharerId, required = false) Integer userId, @RequestParam(defaultValue = "ALL") String state, @RequestParam(defaultValue = "0") Integer from, @RequestParam(defaultValue = "10") Integer size) throws NotFoundException, ValidationException {
        return bookingService.findAllUserBookings(userId, state, from, size);
    }

    @GetMapping("/owner")
    public List<BookingDto> findAllOwnerBookings(@RequestHeader(value = sharerId, required = false) Integer userId, @RequestParam(defaultValue = "ALL") String state, @RequestParam(defaultValue = "0") Integer from, @RequestParam(defaultValue = "10") Integer size) throws NotFoundException, ValidationException {
        return bookingService.findAllOwnerBookings(userId, state, from, size);
    }

}
