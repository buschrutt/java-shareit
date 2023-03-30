package ru.practicum.shareit.booking;

import lombok.experimental.PackagePrivate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.TestContext;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@PackagePrivate
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class BookingRepositoryTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BookingRepository bookingRepository;

    @BeforeEach
    void beforeEach() {
        TestContext.createContext(userRepository, requestRepository, itemRepository, commentRepository, bookingRepository);
    }

    @Test
    void bookingRepositoryBasicsTest() {
        List<Booking> bookings = bookingRepository.findAll();
        assertEquals(bookings.size(), 2);
        Booking booking = bookingRepository.findById(bookings.get(0).getId()).get();
        assertEquals(booking.getId(), bookings.get(0).getId());
    }

    @Test
    void BookingsByBookerTest() {

    }

    @Test
    void BookingsByStatusAndItemIdTest() {

    }

    @Test
    void BookingsByStatusAndBookerAndItemIdTest() {

    }

    @Test
    void BookingsByBookingsByItemIdTest() {

    }


}
