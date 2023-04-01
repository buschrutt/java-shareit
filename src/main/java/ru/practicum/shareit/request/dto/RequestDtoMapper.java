package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.Request;

import java.time.LocalDateTime;
import java.util.List;

public class RequestDtoMapper {

    public static Request addDtoToRequest(RequestDto request, Integer userId) {
        return Request.builder()
                .requesterId(userId)
                .description(request.getDescription())
                .created(LocalDateTime.now())
                .build();
    }

    public static RequestDto addRequestToDto(Request request, List<Item> items) {
        return RequestDto.builder()
                .id(request.getId())
                .requesterId(request.getRequesterId())
                .description(request.getDescription())
                .created(request.getCreated())
                .items(items)
                .build();

    }

}
