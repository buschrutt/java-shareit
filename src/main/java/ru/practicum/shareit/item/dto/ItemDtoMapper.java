package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ItemDtoMapper {

    public static ItemDto toItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .isAvailable(item.getAvailable())
                .build();
    }

    public static ItemDto toItemWithBookingsDto(Item item, List<Booking> itemBookings) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .isAvailable(item.getAvailable())
                .lastBooking(findLastBooking(itemBookings))
                .nextBooking(findNextBooking(itemBookings))
                .build();
    }

    public static ItemDto toItemWithBookingsAndCommentsDto(Item item, List<Booking> itemBookings, List<Comment> itemComments, UserRepository userRepository) {
        List<CommentDto> itemDtoComments = new ArrayList<>();
        for (Comment comment : itemComments) {
            itemDtoComments.add(CommentDtoMapper.toCommentDto(comment, userRepository));
        }
        ItemDto itemDto = ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .isAvailable(item.getAvailable())
                .lastBooking(findLastBooking(itemBookings))
                .nextBooking(findNextBooking(itemBookings))
                .build();
        itemDto.setComments(itemDtoComments);
        return itemDto;
    }

    private static Booking findLastBooking(List<Booking> itemBookings) {
        Booking lastBooking = null;
        for (Booking booking : itemBookings) {
            if (booking.getStart().isBefore(LocalDateTime.now())) {
                if (lastBooking == null) {
                    lastBooking = booking;
                } else if (booking.getStart().isAfter(lastBooking.getStart())) {
                    lastBooking = booking;
                }
            }
        }
        return lastBooking;
    }

    private static Booking findNextBooking(List<Booking> itemBookings) {
        Booking nextBooking = null;
        for (Booking booking : itemBookings) {
            if (booking.getStart().isAfter(LocalDateTime.now())) {
                if (nextBooking == null) {
                    nextBooking = booking;
                } else if (booking.getStart().isBefore(nextBooking.getStart())) {
                    nextBooking = booking;
                }
            }
        }
        return nextBooking;
    }

}
