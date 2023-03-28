package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.dto.RequestDtoMapper;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.repository.RequestRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    @Override
    public RequestDto addRequest(Integer userId, RequestDto requestDto) {
        requestDto.setCreated(LocalDateTime.now());
        Request request = RequestDtoMapper.addDtoToRequest(requestDto, userId);
        requestRepository.save(request);
        log.info("Booking crated, ID: {}", request.getId());
        return RequestDtoMapper.addRequestToDto(request);

    }

    @Override
    public List<RequestDto> findUserRequests(Integer userId) {
        return null;
    }

    @Override
    public List<RequestDto> findAllRequests(Integer from, Integer size, Integer userId) {
        return null;
    }

    @Override
    public RequestDto findRequestById(Integer userId, Integer requestId) {
        return null;
    }
}
