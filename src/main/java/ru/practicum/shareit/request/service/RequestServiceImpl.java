package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestDtoMapper;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    @Override
    public RequestDto addRequest(Integer userId, RequestDto requestDto) throws ValidationException, NotFoundException {
        if (requestDto.getDescription() == null) {
            throw new ValidationException("addRequest: ValidationException--");
        }
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("addRequest: NotFoundException--");
        }
        requestDto.setCreated(LocalDateTime.now());
        return RequestDtoMapper.addRequestToDto(requestRepository.save(RequestDtoMapper.addDtoToRequest(requestDto, userId)));
    }

    @Override
    public List<RequestDto> findUserRequests(Integer userId) throws NotFoundException {
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("findUserRequests: NotFoundException--");
        }
        List<RequestDto> requestDtoList = new ArrayList<>();
        for (Request request : requestRepository.findRequestsByRequesterIdOrderByCreatedDesc(userId)) {
            requestDtoList.add(RequestDtoMapper.addRequestToDto(request));
        }
        return requestDtoList;
    }

    @Override
    public List<RequestDto> findAllRequests(Integer from, Integer size, Integer userId) {
        Pageable pageable = PageRequest.of(from, size);
        List<RequestDto> requestDtoList = new ArrayList<>();
        for (Request request : requestRepository.findRequestsByRequesterIdIsNotOrderByCreatedDesc(userId, pageable)) {
            requestDtoList.add(RequestDtoMapper.addRequestToDto(request));
        }
        return requestDtoList;
    }

    @Override
    public RequestDto findRequestById(Integer userId, Integer requestId) throws NotFoundException {
        if (requestRepository.findById(requestId).isEmpty()) {
            throw new NotFoundException("findRequestById: NotFoundException--");
        }
        Request request = requestRepository.findById(requestId).get();
        return RequestDtoMapper.addRequestToDto(request);
    }
}
