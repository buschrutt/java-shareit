package ru.practicum.shareit;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

public class TestContext {

     public static void createContext(UserRepository userRepository, RequestRepository requestRepository,
                                      ItemRepository itemRepository, CommentRepository commentRepository,
                                      BookingRepository bookingRepository) {
         userRepository.save(User.builder()
                 .name("User1")
                 .email("User1@mail.ru")
                 .build());
         userRepository.save(User.builder()
                 .name("User2")
                 .email("User2@mail.ru")
                 .build());
         userRepository.save(User.builder()
                 .name("User3")
                 .email("User3@mail.ru")
                 .build());

         List<User> users = userRepository.findAll();
         requestRepository.save(Request.builder()
                 .created(LocalDateTime.now())
                 .description("description1")
                 .requesterId(users.get(0).getId())
                 .build());
         requestRepository.save(Request.builder()
                 .created(LocalDateTime.now())
                 .description("description2")
                 .requesterId(users.get(1).getId())
                 .build());
         requestRepository.save(Request.builder()
                 .created(LocalDateTime.now())
                 .description("description3")
                 .requesterId(users.get(0).getId())
                 .build());
         requestRepository.save(Request.builder()
                 .created(LocalDateTime.now())
                 .description("description4")
                 .requesterId(users.get(2).getId())
                 .build());

         List<Request> requests = requestRepository.findAll();
         itemRepository.save(Item.builder()
                 .name("Item1")
                 .description("description")
                 .available(true)
                 .ownerId(users.get(0).getId())
                 .build());
         itemRepository.save(Item.builder()
                 .name("Item2")
                 .description("description")
                 .available(true)
                 .ownerId(users.get(1).getId())
                 .build());
         itemRepository.save(Item.builder()
                 .name("Item3")
                 .description("description")
                 .available(true)
                 .ownerId(users.get(0).getId())
                 .requestId(requests.get(0).getId())
                 .build());
         itemRepository.save(Item.builder()
                 .name("Item4")
                 .description("description")
                 .available(true)
                 .ownerId(users.get(2).getId())
                 .requestId(requests.get(1).getId())
                 .build());

         List<Item> items = itemRepository.findAll();
         LocalDateTime start = LocalDateTime.now();
         LocalDateTime end = start.plusDays(1);
         bookingRepository.save(Booking.builder()
                 .start(start)
                 .end(end)
                 .itemId(items.get(0).getId())
                 .booker(users.get(0).getId())
                 .status("APPROVED")
                 .build());
         bookingRepository.save(Booking.builder()
                 .start(start)
                 .end(end)
                 .itemId(items.get(1).getId())
                 .booker(users.get(1).getId())
                 .status("APPROVED")
                 .build());

         commentRepository.save(Comment.builder()
                 .item(items.get(0).getId())
                 .authorName(users.get(0).getId())
                 .text("Some text1")
                 .created(start)
                 .build());
         commentRepository.save(Comment.builder()
                 .item(items.get(1).getId())
                 .authorName(users.get(1).getId())
                 .text("Some text2")
                 .created(start)
                 .build());
    }

     public static void deleteContext(UserRepository userRepository, RequestRepository requestRepository,
                                     ItemRepository itemRepository, CommentRepository commentRepository,
                                     BookingRepository bookingRepository) {
        userRepository.deleteAll();
        requestRepository.deleteAll();
        itemRepository.deleteAll();
        commentRepository.deleteAll();
        bookingRepository.deleteAll();
    }


}
