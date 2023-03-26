package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.dto.LastOrNextBookingDto;
import ru.practicum.shareit.booking.dto.LastOrNextBookingDtoMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentDtoMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    public ItemDto addItem(Item item, Integer ownerId) throws ValidationException, NotFoundException {
        item.setOwnerId(ownerId);
        itemValidation(item);
        return ItemDtoMapper.toItemDto(itemRepository.save(item));
    }

    @Override
    public ItemDto updateItem(Integer itemId, Item item, Integer ownerId) throws NotFoundException {
        Item newItem;
        if (!userRepository.existsById(ownerId)) {
            throw new NotFoundException("updateItem: No User Found--");
        }
        if (itemRepository.findById(itemId).isPresent()) {
            newItem = itemRepository.findById(itemId).get();
        } else {
            throw new NotFoundException("updateItem: No Item Found--");
        }
        if (item.getName() != null) {
            newItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            newItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            newItem.setAvailable(item.getAvailable());
        }
        return ItemDtoMapper.toItemDto(itemRepository.save(newItem));
    }

    @Override
    public List<ItemDto> getAllUserItems(Integer ownerId) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        List<Item> items = itemRepository.findItemsByOwnerId(ownerId);
        for (Item item : items) {
            List<Booking> itemBookings = new ArrayList<>();
            if (Objects.equals(itemRepository.findById(item.getId()).get().getOwnerId(), ownerId)) {
                itemBookings = bookingRepository.findBookingsByStatusAndItemIdOrderByStartDesc("APPROVED", item.getId());
            }
            List<Comment> comments = commentRepository.findCommentsByItemOrderByCreatedDesc(item.getId());
            itemDtoList.add(ItemDtoMapper.toItemWithBookingsAndCommentDtos(item, convertCommentsToDto(comments), findLastOrNextBooking(itemBookings, true), findLastOrNextBooking(itemBookings, false)));
        }
        return itemDtoList;
    }

    @Override
    public ItemDto getItemById(Integer itemId, Integer ownerId) throws NotFoundException {
        List<Comment> comments = commentRepository.findCommentsByItemOrderByCreatedDesc(itemId);
        List<Booking> itemBookings = new ArrayList<>();
        if (itemRepository.findById(itemId).isPresent()) {
            if (Objects.equals(itemRepository.findById(itemId).get().getOwnerId(), ownerId)) {
                itemBookings = bookingRepository.findBookingsByStatusAndItemIdOrderByStartDesc("APPROVED", itemId);
            }
        }
        if (itemRepository.findById(itemId).isPresent()) {
            return ItemDtoMapper.toItemWithBookingsAndCommentDtos(itemRepository.findById(itemId).get(), convertCommentsToDto(comments), findLastOrNextBooking(itemBookings, true), findLastOrNextBooking(itemBookings, false)); // true if next booking
        } else {
            throw new NotFoundException("getItemById: No Item Found--");
        }
    }

    @Override
    public List<ItemDto> getItemsSearched(String text) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        if (text.isEmpty()) {
            return itemDtoList;
        }
        List<Item> items = itemRepository.findAll();
        for (Item item : items) {
            if (item.getAvailable() && (item.getName().toLowerCase().contains(text.toLowerCase()) || item.getDescription().toLowerCase().contains(text.toLowerCase()))) {
                itemDtoList.add(ItemDtoMapper.toItemDto(item));
            }
        }
        return itemDtoList;
    }

    @Override
    public CommentDto addComment(Comment comment, Integer userId, Integer itemId) throws ValidationException {
        commentValidation(comment, userId, itemId);
        comment.setAuthorName(userId);
        comment.setItem(itemId);
        comment.setCreated(LocalDateTime.now());
        return CommentDtoMapper.toCommentDto(commentRepository.save(comment), userRepository);
    }

    // %%%%%%%%%% %%%%%%%%%% supporting methods %%%%%%%%%% %%%%%%%%%%

    private LastOrNextBookingDto findLastOrNextBooking(List<Booking> itemBookings, Boolean isNext) {
        LastOrNextBookingDto bookingDto = null;
        if (isNext) {
            for (Booking booking : itemBookings) {
                if (booking.getStart().isAfter(LocalDateTime.now())) {
                    if (bookingDto == null) {
                        bookingDto = LastOrNextBookingDtoMapper.addBookingToDto(booking, userRepository.findById(booking.getBooker()).get(), itemRepository.findById(booking.getItemId()).get());
                    } else if (booking.getStart().isBefore(bookingDto.getStart())) {
                        bookingDto = LastOrNextBookingDtoMapper.addBookingToDto(booking, userRepository.findById(booking.getBooker()).get(), itemRepository.findById(booking.getItemId()).get());
                    }
                }
            }
        } else {
            for (Booking booking : itemBookings) {
                if (booking.getStart().isBefore(LocalDateTime.now())) {
                    if (bookingDto == null) {
                        bookingDto = LastOrNextBookingDtoMapper.addBookingToDto(booking, userRepository.findById(booking.getBooker()).get(), itemRepository.findById(booking.getItemId()).get());
                    } else if (booking.getStart().isAfter(bookingDto.getStart())) {
                        bookingDto = LastOrNextBookingDtoMapper.addBookingToDto(booking, userRepository.findById(booking.getBooker()).get(), itemRepository.findById(booking.getItemId()).get());
                    }
                }
            }
        }
        return bookingDto;
    }

    private List<CommentDto> convertCommentsToDto(List<Comment> comments){
        List<CommentDto> itemCommentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            itemCommentDtos.add(CommentDtoMapper.toCommentDto(comment, userRepository));
        }
        return itemCommentDtos;
    }

    private void itemValidation(Item item) throws ValidationException, NotFoundException {
        if (item.getName().isEmpty() || item.getDescription() == null || item.getAvailable() == null) {
            throw new ValidationException("itemValidation: Name Or Description Is Empty--");
        }
        if (!userRepository.existsById(item.getOwnerId())) {
            throw new NotFoundException("getUserById: No User Found--");
        }
    }

    void commentValidation(Comment comment, Integer userId, Integer itemId) throws ValidationException {
        if (comment.getText().isEmpty()) {
            throw new ValidationException("commentValidation: text is Empty--");
        }
        List<Booking> endBookingTimes = bookingRepository.findBookingsByStatusAndBookerAndItemIdOrderByStartDesc("APPROVED", userId, itemId);
        for (Booking booking : endBookingTimes) {
            if (booking.getEnd().isBefore(LocalDateTime.now())) {
                return;
            }
        }
        throw new ValidationException("commentValidation: no Completed Booking Found--");
    }

}
