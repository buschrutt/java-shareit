package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
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
    private final ItemRepository itemRepository;

    @Override
    public RequestDto addRequest(Integer userId, RequestDto requestDto) throws ValidationException, NotFoundException {
        if (requestDto.getDescription() == null) {
            throw new ValidationException("addRequest: ValidationException--");
        }
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("addRequest: NotFoundException--");
        }
        requestDto.setCreated(LocalDateTime.now());
        List<Item> items = new ArrayList<>();
        return RequestDtoMapper.addRequestToDto(requestRepository.save(RequestDtoMapper.addDtoToRequest(requestDto, userId)), items);
    }

    @Override
    public List<RequestDto> findUserRequests(Integer userId) throws NotFoundException {
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("findUserRequests: NotFoundException--");
        }
        List<RequestDto> requestDtoList = new ArrayList<>();
        for (Request request : requestRepository.findRequestsByRequesterIdOrderByCreatedDesc(userId)) {
            List<Item> items = itemRepository.findItemsByRequestIdOrderById(request.getId());
            requestDtoList.add(RequestDtoMapper.addRequestToDto(request, items));
        }
        return requestDtoList;
    }

    @Override
    public List<RequestDto> findAllRequests(Integer from, Integer size, Integer userId) throws ValidationException {
        if (size == 0 || from < 0) {
            throw new ValidationException("findAllRequests: ValidationException--");
        }
        Pageable pageable = PageRequest.of(from / size, size);
        List<RequestDto> requestDtoList = new ArrayList<>();
        for (Request request : requestRepository.findRequestsByRequesterIdIsNotOrderByCreatedDesc(userId, pageable)) {
            List<Item> items = itemRepository.findItemsByRequestIdOrderById(request.getId());
            requestDtoList.add(RequestDtoMapper.addRequestToDto(request, items));
        }
        return requestDtoList;
    }

    @Override
    public RequestDto findRequestById(Integer userId, Integer requestId) throws NotFoundException {
        if (requestRepository.findById(requestId).isEmpty() || userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("findRequestById: NotFoundException--");
        }
        Request request = requestRepository.findById(requestId).get();
        List<Item> items = itemRepository.findItemsByRequestIdOrderById(request.getId());
        return RequestDtoMapper.addRequestToDto(request, items);
    }

    // %%%%%%%%%% %%%%%%%%%% supporting methods %%%%%%%%%% %%%%%%%%%%

}
