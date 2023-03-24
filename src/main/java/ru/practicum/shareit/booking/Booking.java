package ru.practicum.shareit.booking;

import lombok.Data;
import lombok.experimental.PackagePrivate;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */

@Data
// @Builder
@PackagePrivate
public class Booking {
    int id;
    LocalDateTime start;
    LocalDateTime end;
    int item;
    int booker;
    String status; // WAITING — new booking, await for an approval; APPROVED — by owner
    // REJECTED — by owner; CANCELED — by requester;
}
