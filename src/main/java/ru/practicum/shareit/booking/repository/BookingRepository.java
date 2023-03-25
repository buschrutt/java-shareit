package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query(
            value = "SELECT * FROM bookings WHERE bookings.booker_id = ? ORDER BY bookings.start_date DESC",
            nativeQuery = true)
    List<Booking> userBookingsSorted(Integer user_id);

    @Query(
            value = "SELECT * FROM bookings WHERE bookings.item_id IN (SELECT id FROM items WHERE items.owner_id = ?) ORDER BY bookings.start_date DESC",
            nativeQuery = true)
    List<Booking> ownerBookingsSorted(Integer owner_id);

    @Query(
            value = "SELECT * FROM bookings WHERE bookings.status = 'APPROVED' ORDER BY bookings.start_date DESC",
            nativeQuery = true)
    List<Booking> itemBookings(Integer item_id);

    @Query(
            value = "SELECT * FROM bookings WHERE bookings.status = 'APPROVED' AND booker_id = ? AND item_id = ? ORDER BY bookings.start_date DESC",
            nativeQuery = true)
    List<Booking> userItemBookings(Integer userId, Integer itemId);

}
