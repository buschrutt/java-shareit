package ru.practicum.shareit.item;

import lombok.experimental.PackagePrivate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.practicum.shareit.TestContext;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@PackagePrivate
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ItemRepositoryTests {

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
    void itemRepositoryFindByOwnerTest() {
        List<Item> items = itemRepository.findAll();
        List<Item> itemsByOwner = itemRepository.findItemsByOwnerId(items.get(0).getOwnerId());
        assertEquals(itemsByOwner.size(), 2);
    }

    @Test
    void findItemsByRequestIdOrderByIdTest() {
        List<Request> requests = requestRepository.findAll();
        List<Item> itemsByRequest = itemRepository.findItemsByRequestIdOrderById(requests.get(0).getId());
        assertEquals(itemsByRequest.size(), 1);
    }

    @Test
    void itemRepositoryUpdateItemTest() {
        List<Item> items = itemRepository.findAll();
        Item updatedItem = itemRepository.findById(items.get(1).getId()).get();
        updatedItem.setDescription("description fine");
        itemRepository.save(updatedItem);
        updatedItem = itemRepository.findById(items.get(1).getId()).get();
        assertEquals(updatedItem.getDescription(), "description fine");
    }

    @Test
    void commentRepositoryTest() {
        List<Item> items = itemRepository.findAll();
        List<Comment> comments = commentRepository.findCommentsByItemOrderByCreatedDesc(items.get(0).getId());
        assertEquals(comments.size(), 1);
        assertEquals(comments.get(0).getText(), "Some text1");
    }

}
