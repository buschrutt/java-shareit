package ru.practicum.shareit.request;

import lombok.experimental.PackagePrivate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.TestContext;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@PackagePrivate
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class RequestRepositoryTests {

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

    @AfterEach
    void afterEach() {
        TestContext.deleteContext(userRepository, requestRepository, itemRepository, commentRepository, bookingRepository);
    }

    @Test
    void requestRepositoryTest() {
        Pageable pageable = PageRequest.of(0, 3);
        List<Request> requestDtoList = requestRepository.findRequestsByRequesterIdIsNotOrderByCreatedDesc(2, pageable);
        assertEquals(requestDtoList.size(), 3);
        //assertTrue(requestDtoList.get(0).getCreated().isAfter(requestDtoList.get(1).getCreated()));
        requestDtoList = requestRepository.findRequestsByRequesterIdOrderByCreatedDesc(1);
        assertEquals(requestDtoList.size(), 2);
        assertTrue(requestDtoList.get(0).getCreated().isAfter(requestDtoList.get(1).getCreated()));
    }


}
