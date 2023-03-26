package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

public class LastOrNextBookingDtoMapper {

    public static LastOrNextBookingDto addBookingToDto(Booking booking, UserRepository userRepository, ItemRepository itemRepository) {
        User user = null;
        Item item = null;
        if (userRepository.findById(booking.getBooker()).isPresent()) {
            user = userRepository.findById(booking.getBooker()).get();
        }
        if (itemRepository.findById(booking.getItemId()).isPresent()) {
            item = itemRepository.findById(booking.getItemId()).get();
        }
        return LastOrNextBookingDto.builder()
                .id(booking.getId())
                .start(booking.getStart())
                .end(booking.getEnd())
                .bookerId(user.getId())
                .item(item)
                .status(booking.getStatus())
                .build();

    }

}
