package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.PackagePrivate;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
@Builder
@PackagePrivate
public class BookingDto {
    Integer id;
    LocalDateTime start;
    LocalDateTime end;
    Item item;
    User booker;
    String status;
}
