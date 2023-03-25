package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.booking.dto.LastNextBookingDto;
import ru.practicum.shareit.booking.dto.LastNextBookingDtoMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
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

    public static ItemDto toItemWithBookingsAndCommentsDto(Item item, List<Booking> itemBookings, List<Comment> itemComments, UserRepository userRepository, ItemRepository itemRepository) {
        List<CommentDto> itemDtoComments = new ArrayList<>();
        for (Comment comment : itemComments) {
            itemDtoComments.add(CommentDtoMapper.toCommentDto(comment, userRepository));
        }
        ItemDto itemDto = ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .isAvailable(item.getAvailable())
                .lastBooking(findLastBooking(itemBookings, userRepository, itemRepository))
                .nextBooking(findNextBooking(itemBookings, userRepository, itemRepository))
                .build();
        itemDto.setComments(itemDtoComments);
        return itemDto;
    }

    private static LastNextBookingDto findLastBooking(List<Booking> itemBookings, UserRepository userRepository, ItemRepository itemRepository) {
        if (itemBookings.isEmpty()) {
            return null;
        }
        LastNextBookingDto lastBooking = null;
        for (Booking booking : itemBookings) {
            if (booking.getStart().isBefore(LocalDateTime.now())) {
                if (lastBooking == null) {
                    lastBooking = LastNextBookingDtoMapper.addBookingToDto(booking, userRepository, itemRepository);
                } else if (booking.getStart().isAfter(lastBooking.getStart())) {
                    lastBooking = LastNextBookingDtoMapper.addBookingToDto(booking, userRepository, itemRepository);
                }
            }
        }
        return lastBooking;
    }

    private static LastNextBookingDto findNextBooking(List<Booking> itemBookings, UserRepository userRepository, ItemRepository itemRepository) {
        LastNextBookingDto nextBooking = null;
        for (Booking booking : itemBookings) {
            if (booking.getStart().isAfter(LocalDateTime.now())) {
                if (nextBooking == null) {
                    nextBooking = LastNextBookingDtoMapper.addBookingToDto(booking, userRepository, itemRepository);
                } else if (booking.getStart().isBefore(nextBooking.getStart())) {
                    nextBooking = LastNextBookingDtoMapper.addBookingToDto(booking, userRepository, itemRepository);
                }
            }
        }
        return nextBooking;
    }

}