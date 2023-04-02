package ru.practicum.shareit.item;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.practicum.shareit.booking.dto.LastOrNextBookingDtoMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentDtoMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemServiceTests {
    @Mock
    BookingRepository bookingRepository;
    @Mock
    CommentRepository commentRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ItemRepository itemRepository;
    @InjectMocks
    ItemServiceImpl itemServiceImpl;
    private final User user = User.builder()
            .id(1)
            .name("John")
            .email("john.doe@mail.com")
            .build();
    private final Item item = Item.builder()
            .id(1)
            .name("ItemName")
            .description("description")
            .available(true)
            .requestId(1)
            .ownerId(1)
            .build();
    private final ItemDto itemDto = ItemDto.builder()
            .name("ItemName")
            .description("description")
            .available(true)
            .requestId(1)
            .ownerId(2)
            .build();
    private final LocalDateTime timeStart = LocalDateTime.now();
    private final LocalDateTime timeEnd = LocalDateTime.now();
    private final Comment comment = Comment.builder()
            .id(1)
            .text("Some_text")
            .item(2)
            .authorName(1)
            .created(timeEnd)
            .build();
    private final CommentDto commentDto = CommentDto.builder()
            .id(1)
            .text("Some_text")
            .item(2)
            .authorName("John")
            .created(timeEnd)
            .build();
    private final Booking booking = Booking.builder()
            .itemId(2)
            .start(timeStart)
            .end(timeEnd)
            .booker(1)
            .status("WAITING")
            .build();
    private final Booking bookingNext = Booking.builder()
            .itemId(2)
            .start(timeStart.plusDays(2))
            .end(timeEnd.plusDays(2))
            .booker(2)
            .status("WAITING")
            .build();


    @Test
    @SneakyThrows
    void addItemUnitTest() {
        when(itemRepository.save(any())).thenReturn(item);
        when(userRepository.existsById(any())).thenReturn(true);
        itemDto.setId(1);
        ItemDto itemDto0 = itemServiceImpl.addItem(itemDto, 2);
        itemDto0.setOwnerId(2);
        assertEquals(itemDto0.toString(), itemDto.toString());
    }

    @Test
    @SneakyThrows
    void updateItemUnitTest() {
        when(userRepository.existsById(any())).thenReturn(true);
        when(itemRepository.findById(any())).thenReturn(Optional.ofNullable(item));
        when(itemRepository.save(any())).thenReturn(item);
        itemDto.setId(1);
        ItemDto itemDto0 = itemServiceImpl.updateItem(1, itemDto, 2);
        itemDto0.setOwnerId(2);
        assertEquals(itemDto0.toString(), itemDto.toString());
    }

    @Test
    @SneakyThrows
    void updateItemUserNotFoundUnitTest() {
        when(userRepository.existsById(any())).thenReturn(false);
        when(itemRepository.findById(any())).thenReturn(Optional.ofNullable(item));
        when(itemRepository.save(any())).thenReturn(item);
        try {
            itemServiceImpl.updateItem(1, itemDto, 2);
            fail();
        } catch (NotFoundException thrown) {
            assertTrue(true);
        }
    }

    @Test
    @SneakyThrows
    void updateItemNotFoundUnitTest() {
        when(userRepository.existsById(any())).thenReturn(true);
        when(itemRepository.findById(any())).thenReturn(Optional.empty());
        when(itemRepository.save(any())).thenReturn(item);
        try {
            itemServiceImpl.updateItem(1, itemDto, 2);
            fail();
        } catch (NotFoundException thrown) {
            assertTrue(true);
        }
    }

    @Test
    @SneakyThrows
    void getAllUserItemsUnitTest() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        List<Booking> bookingList = new ArrayList<>();
        //bookingList.add(booking);
        List<Comment> commentList = new ArrayList<>();
        List<CommentDto> commentListDto = new ArrayList<>();
        //commentList.add(comment);
        when(itemRepository.findById(any())).thenReturn(Optional.ofNullable(item));
        when(itemRepository.findItemsByOwnerId(any())).thenReturn(itemList);
        when(bookingRepository.findBookingsByStatusAndItemIdOrderByStartDesc(any(), any())).thenReturn(bookingList);
        when(commentRepository.findCommentsByItemOrderByCreatedDesc(any())).thenReturn(commentList);
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        ItemDto itemDto0 = itemServiceImpl.getAllUserItems(1).get(0);
        itemDto0.setOwnerId(2);
        itemDto.setId(1);
        itemDto.setComments(commentListDto);
        assertEquals(itemDto0.toString(), itemDto.toString());
    }

    @Test
    @SneakyThrows
    void getItemByIdUnitTest() {
        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        bookingList.add(bookingNext);
        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment);
        List<CommentDto> commentDtoList = new ArrayList<>();
        commentDtoList.add(CommentDtoMapper.toCommentDto(comment, "John"));
        List<CommentDto> commentListDto = new ArrayList<>();
        when(itemRepository.findById(any())).thenReturn(Optional.ofNullable(item));
        when(bookingRepository.findBookingsByStatusAndItemIdOrderByStartDesc(any(), any())).thenReturn(bookingList);
        when(commentRepository.findCommentsByItemOrderByCreatedDesc(any())).thenReturn(commentList);
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));ItemDto itemDto0 = itemServiceImpl.getItemById(1, 1);
        itemDto0.setOwnerId(2);
        itemDto.setId(1);
        itemDto.setComments(commentListDto);
        assert user != null;
        itemDto.setLastBooking(LastOrNextBookingDtoMapper.addBookingToDto(booking, user, item));
        itemDto.setNextBooking(LastOrNextBookingDtoMapper.addBookingToDto(bookingNext, user, item));
        itemDto.setComments(commentDtoList);
        assertEquals(itemDto0.toString(), itemDto.toString());
    }

    @Test
    @SneakyThrows
    void getItemsSearchedUnitTest() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemRepository.findAll()).thenReturn(itemList);
        ItemDto itemDto0 = itemServiceImpl.getItemsSearched("desc").get(0);
        itemDto0.setOwnerId(2);
        itemDto.setId(1);
        assertEquals(itemDto0.toString(), itemDto.toString());
    }

    @Test
    @SneakyThrows
    void addCommentUnitTest() {
        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        when(commentRepository.save(any())).thenReturn(comment);
        when(bookingRepository.findBookingsByStatusAndBookerAndItemIdOrderByStartDesc(any(), any(), any())).thenReturn(bookingList);
        assertEquals(itemServiceImpl.addComment(commentDto, 1, 1).toString(), commentDto.toString());
    }

}
