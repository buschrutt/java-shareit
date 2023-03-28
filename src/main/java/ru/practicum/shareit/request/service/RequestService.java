package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.model.Request;

import java.util.List;

public interface RequestService {

    RequestDto addRequest(Integer userId, RequestDto request);

    List<RequestDto> findUserRequests(Integer userId);

    List<RequestDto> findAllRequests(Integer from, Integer size, Integer userId);

    RequestDto findRequestById(Integer userId, Integer requestId);

}
