package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findBookingsByBookerOrderByStartDesc(Integer userId);

    List<Booking> findBookingsByStatusAndItemIdOrderByStartDesc(String status, Integer itemId);

    List<Booking> findBookingsByStatusAndBookerAndItemIdOrderByStartDesc(String status, Integer userId, Integer itemId);

    List<Booking> findBookingsByItemIdInOrderByStartDesc(List<Integer> items);

    /* !!!SAMPLE --DO NOT DELETE!!!
    @Query(
            value = "SELECT * FROM bookings WHERE bookings.item_id IN (SELECT id FROM items WHERE items.owner_id = ?) ORDER BY bookings.start_date DESC",
            nativeQuery = true)
    List<Booking> ownerBookingsSorted(Integer ownerId);sx x djjj
    */
}
