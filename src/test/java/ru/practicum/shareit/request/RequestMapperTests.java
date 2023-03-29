package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestDtoMapper;
import ru.practicum.shareit.request.model.Request;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestMapperTests {

    @Test
    void addDtoToRequestTest() {
        Integer userId = 1;
        Request request = Request.builder()
                .requesterId(userId)
                .description("some")
                .build();
        RequestDto requestDto = RequestDto.builder()
                .description("some")
                .build();
        Request requestBuilt = RequestDtoMapper.addDtoToRequest(requestDto, userId);
        request.setCreated(requestBuilt.getCreated());
        assertEquals(request.toString(), requestBuilt.toString());
    }

    @Test
    void addRequestToDto() {
        LocalDateTime timeNow = LocalDateTime.now();
        Request request = Request.builder()
                .id(1)
                .requesterId(2)
                .description("Some_Request")
                .created(timeNow)
                .build();
        Item item = Item.builder()
                .id(1)
                .name("Item_Name")
                .description("Some_Item")
                .available(true)
                .ownerId(2)
                .requestId(3)
                .build();
        List<Item> items = new ArrayList<>();
        items.add(item);
        RequestDto requestDto = RequestDto.builder()
                .id(1)
                .requesterId(2)
                .description("Some_Request")
                .created(timeNow)
                .items(items)
                .build();
        RequestDto requestDtoBuilt = RequestDtoMapper.addRequestToDto(request, items);
        assertEquals(requestDtoBuilt.getId(), requestDto.getId());
    }

}
