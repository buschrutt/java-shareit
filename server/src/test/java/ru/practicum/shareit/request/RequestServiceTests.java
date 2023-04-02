package ru.practicum.shareit.request;

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
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.request.service.RequestServiceImpl;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestServiceTests {
    @Mock
    RequestRepository requestRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ItemRepository itemRepository;
    @InjectMocks
    RequestServiceImpl requestServiceImpl;
    private final LocalDateTime created = LocalDateTime.now();
    private final RequestDto requestDto = RequestDto.builder()
            .id(1)
            .description("description")
            .items(new ArrayList<>())
            .build();
    private final Request request = Request.builder()
            .id(1)
            .description("description")
            .build();
    private final User user = User.builder()
            .id(1)
            .name("John")
            .email("john.doe@mail.com")
            .build();
    private final Item item = Item.builder()
            .name("ItemName")
            .description("description")
            .available(true)
            .requestId(1)
            .ownerId(2)
            .build();

    @Test
    @SneakyThrows
    void addRequestUnitTest() {
        when(requestRepository.save(any())).thenReturn(request);
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        RequestDto requestDto0 = requestServiceImpl.addRequest(1, requestDto);
        requestDto0.setCreated(created);
        requestDto.setCreated(created);
        assertEquals(requestDto0.toString(), requestDto.toString());
    }

    @Test
    @SneakyThrows
    void findUserRequestsUnitTest() {
        List<Request> requestList = new ArrayList<>();
        requestList.add(request);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(requestRepository.findRequestsByRequesterIdOrderByCreatedDesc(any())).thenReturn(requestList);
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        when(itemRepository.findItemsByRequestIdOrderById(any())).thenReturn(itemList);
        List<RequestDto> requestListDto = requestServiceImpl.findUserRequests(1);
        requestListDto.get(0).setCreated(created);
        requestDto.setCreated(created);
        requestDto.setItems(itemList);
        assertEquals(requestListDto.get(0).toString(), requestDto.toString());
    }

    @Test
    @SneakyThrows
    void findAllRequestsUnitTest() {
        List<Request> requestList = new ArrayList<>();
        requestList.add(request);
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(requestRepository.findRequestsByRequesterIdIsNotOrderByCreatedDesc(any(), any())).thenReturn(requestList);
        when(itemRepository.findItemsByRequestIdOrderById(any())).thenReturn(itemList);
        List<RequestDto> requestListDto = requestServiceImpl.findAllRequests(0, 10, 1);
        requestListDto.get(0).setCreated(created);
        requestDto.setCreated(created);
        requestDto.setItems(itemList);
        assertEquals(requestListDto.get(0).toString(), requestDto.toString());
    }

    @Test
    @SneakyThrows
    void findRequestByIdtUnitTest() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        when(requestRepository.findById(any())).thenReturn(Optional.ofNullable(request));
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(user));
        when(requestRepository.findById(any())).thenReturn(Optional.ofNullable(request));
        when(itemRepository.findItemsByRequestIdOrderById(any())).thenReturn(itemList);
        RequestDto requestDto0 = requestServiceImpl.findRequestById(1, 2);
        requestDto0.setCreated(created);
        requestDto.setCreated(created);
        requestDto.setItems(itemList);
        assertEquals(requestDto0.toString(), requestDto.toString());
    }


}
