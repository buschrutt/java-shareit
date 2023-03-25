package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.PackagePrivate;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */

@Data
@Builder
@PackagePrivate
public class ItemDto {
    int id;
    String name;
    String description;
    boolean isAvailable;
    int ownerId;
    String requestLink; // if was created by user request - holds the link to that request
    Booking lastBooking;
    Booking nextBooking;
    List<CommentDto> comments;
}
