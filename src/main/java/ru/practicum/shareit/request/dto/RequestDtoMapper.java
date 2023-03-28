package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.request.model.Request;

import java.time.LocalDateTime;

public class RequestDtoMapper {

    public static Request addDtoToRequest(RequestDto request, Integer userId) {
        return Request.builder()
                .requesterId(userId)
                .description(request.getDescription())
                .created(LocalDateTime.now())
                .build();
    }

    public static RequestDto addRequestToDto(Request request) {
        return RequestDto.builder()
                .id(request.getId())
                .requesterId(request.getId())
                .description(request.getDescription())
                .created(request.getCreated())
                .build();

    }

}
