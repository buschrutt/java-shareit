package ru.practicum.shareit.request.service;

import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.request.dto.RequestDto;

import java.util.List;

public interface RequestService {

    RequestDto addRequest(Integer userId, RequestDto requestDto) throws ValidationException, NotFoundException;

    List<RequestDto> findUserRequests(Integer userId) throws NotFoundException;

    List<RequestDto> findAllRequests(Integer from, Integer size, Integer userId) throws ValidationException;

    RequestDto findRequestById(Integer userId, Integer requestId) throws NotFoundException;

}
