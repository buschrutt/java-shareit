package ru.practicum.shareit.request;

import lombok.experimental.PackagePrivate;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@PackagePrivate
public class RequestController {

    final RequestService requestService;
    final UserRepository userRepository;
    final RequestRepository requestRepository;
    final String sharerId = "X-Sharer-User-Id";

    public RequestController(RequestService requestService, UserRepository userRepository, RequestRepository requestRepository) {
        this.requestService = requestService;
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }

    @PostMapping
    public RequestDto addRequest(@RequestHeader(value = sharerId) Integer userId, @RequestBody RequestDto requestDto) throws ValidationException, NotFoundException {
        if (requestDto.getDescription() == null) {
            throw new ValidationException("addRequest: ValidationException-- requestDto Description holds null ");
        }
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("addRequest: NotFoundException-- userId: " + userId);
        }
        return requestService.addRequest(userId, requestDto);
    }

    @GetMapping
    public List<RequestDto> findUserRequests(@RequestHeader(value = sharerId) Integer userId) throws NotFoundException {
        if (userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("findUserRequests: NotFoundException-- userId: " + userId);
        }
        return requestService.findUserRequests(userId);
    }

    @GetMapping("/all")
    public List<RequestDto> findAllRequests(@RequestHeader(value = sharerId) Integer userId, @RequestParam(defaultValue = "0") Integer from, @RequestParam(defaultValue = "10") Integer size) throws ValidationException {
        if (userId == 4) {
            int a = 2;
        }
        if (size == 0 || from < 0) {
            throw new ValidationException("findAllRequests: ValidationException-- RequestParam has invalid size or from values");
        }
        return requestService.findAllRequests(from, size, userId);
    }

    @GetMapping("/{requestId}")
    public RequestDto findRequestById(@RequestHeader(value = sharerId) Integer userId, @PathVariable Integer requestId) throws NotFoundException {
        if (requestRepository.findById(requestId).isEmpty() || userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("findRequestById: NotFoundException-- User or Request isn't found, requestId: " + requestId + ", userId: " + userId);
        }
        return requestService.findRequestById(userId, requestId);
    }

}
