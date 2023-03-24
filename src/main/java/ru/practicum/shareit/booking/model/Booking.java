package ru.practicum.shareit.booking.model;

import lombok.*;
import lombok.experimental.PackagePrivate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */

@Entity
@Builder
@Table(name = "bookings")
@Getter @Setter @ToString
@PackagePrivate
@AllArgsConstructor @NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "start_date")
    LocalDateTime start;

    @Column(name = "end_date")
    LocalDateTime end;

    @Column(name = "item_id")
    int itemId;

    @Column(name = "booker_id")
    int booker;

    @Column(name = "status")
    String status; // WAITING — new booking, await for an approval; APPROVED — by owner
    // REJECTED — by owner; CANCELED — by requester;
}
