package ru.practicum.shareit.booking;

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
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequestDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
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
public class BookingServiceTests {
    @Mock
    BookingRepository bookingRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ItemRepository itemRepository;
    @InjectMocks
    BookingServiceImpl bookingServiceImpl;
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
            .ownerId(2)
            .build();
    private final LocalDateTime timeStart = LocalDateTime.now().plusDays(1);
    private final LocalDateTime timeEnd = LocalDateTime.now().plusDays(2);
    private final Booking booking = Booking.builder()
            .itemId(2)
            .start(timeStart)
            .end(timeEnd)
            .booker(1)
            .status("WAITING")
            .build();
    private final BookingDto bookingDto = BookingDto.builder()
            .item(item)
            .start(timeStart)
            .end(timeEnd)
            .booker(user)
            .status("WAITING")
            .build();
    private final BookingRequestDto bookingRequestDto = BookingRequestDto.builder()
            .itemId(2)
            .start(timeStart)
            .end(timeEnd)
            .build();

    @Test
    @SneakyThrows
    void addBookingUnitTest() {
        when(userRepository.existsById(any())).thenReturn(true);
        when(itemRepository.findById(any())).thenReturn(Optional.ofNullable(item));
        when(bookingRepository.save(any())).thenReturn(booking);
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        assertEquals(bookingServiceImpl.addBooking(bookingRequestDto, 1).toString(), bookingDto.toString());
    }

    @Test
    @SneakyThrows
    void bookingApprovalUnitTest() {
        when(userRepository.existsById(any())).thenReturn(true);
        when(bookingRepository.findById(any())).thenReturn(Optional.ofNullable(booking));
        when(itemRepository.findById(any())).thenReturn(Optional.ofNullable(item));
        when(bookingRepository.save(any())).thenReturn(booking);
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        bookingDto.setStatus("APPROVED");
        assertEquals(bookingServiceImpl.bookingApproval(2, 1, true).toString(), bookingDto.toString());
        bookingDto.setStatus("REJECTED");
        try {
            bookingServiceImpl.bookingApproval(2, 1, false);
            fail();
        } catch (ValidationException thrown) {
            assertTrue(true);
        }
        bookingDto.setStatus("APPROVED");
        when(userRepository.existsById(any())).thenReturn(false);
        try {
            bookingServiceImpl.bookingApproval(2, 1, true);
            fail();
        } catch (ValidationException thrown) {
            assertTrue(true);
        }
        when(userRepository.existsById(any())).thenReturn(true);
        try {
            bookingServiceImpl.bookingApproval(2, 1, false);
            fail();
        } catch (ValidationException thrown) {
            assertTrue(true);
        }
    }

    @Test
    @SneakyThrows
    void findBookingByIdUnitTest() {
        when(bookingRepository.findById(any())).thenReturn(Optional.ofNullable(booking));
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        when(itemRepository.findById(any())).thenReturn(Optional.ofNullable(item));
        assertEquals(bookingServiceImpl.findBookingById(2, 1).toString(), bookingDto.toString());
        when(bookingRepository.findById(any())).thenReturn(Optional.empty());
        try {
            bookingServiceImpl.findBookingById(2, 1);
            fail();
        } catch (NotFoundException thrown) {
            assertTrue(true);
        }
    }

    @Test
    @SneakyThrows
    void findAllUserBookingsUnitTest() {
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        when(itemRepository.findById(any())).thenReturn(Optional.ofNullable(item));
        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(userRepository.existsById(any())).thenReturn(true);
        when(bookingRepository.findBookingsByBookerOrderByStartDesc(any(), any())).thenReturn(bookingList);
        List<BookingDto> bookingDtoList = bookingServiceImpl.findAllUserBookings(2, "ALL", 0, 5);
        assertEquals(bookingDtoList.get(0).toString(), bookingDto.toString());
    }

    @Test
    @SneakyThrows
    void findAllOwnerBookingsUnitTest() {
        when(userRepository.existsById(any())).thenReturn(true);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(itemRepository.findItemsByOwnerIdOrderById(any())).thenReturn(itemList);
        List<Booking> bookingList = new ArrayList<>();
        bookingList.add(booking);
        when(bookingRepository.findBookingsByItemIdInOrderByStartDesc(any(), any())).thenReturn(bookingList);
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        when(itemRepository.findById(any())).thenReturn(Optional.ofNullable(item));
        List<BookingDto> bookingDtoList = bookingServiceImpl.findAllOwnerBookings(2, "ALL", 0, 5);
        assertEquals(bookingDtoList.get(0).toString(), bookingDto.toString());
        bookingDtoList = bookingServiceImpl.findAllOwnerBookings(2, "CURRENT", 0, 5);
        assertEquals(bookingDtoList.size(), 0);
        bookingDtoList = bookingServiceImpl.findAllOwnerBookings(2, "PAST", 0, 5);
        assertEquals(bookingDtoList.size(), 0);
        bookingDtoList = bookingServiceImpl.findAllOwnerBookings(2, "FUTURE", 0, 5);
        assertEquals(bookingDtoList.get(0).toString(), bookingDto.toString());
        bookingDtoList = bookingServiceImpl.findAllOwnerBookings(2, "WAITING", 0, 5);
        assertEquals(bookingDtoList.get(0).toString(), bookingDto.toString());
        bookingDtoList = bookingServiceImpl.findAllOwnerBookings(2, "REJECTED", 0, 5);
        assertEquals(bookingDtoList.size(), 0);
        try {
            bookingServiceImpl.findAllOwnerBookings(2, "REJ", 0, 5);
            fail();
        } catch (ValidationException thrown) {
            assertTrue(true);
        }

    }

}
