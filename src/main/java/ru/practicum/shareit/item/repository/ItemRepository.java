package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer>  {

    @Query(
            value = "SELECT * FROM items WHERE owner_id = ? ORDER BY items.name DESC",
            nativeQuery = true)
    List<Item> ownerItems(Integer owner_id);

}
