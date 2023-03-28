package ru.practicum.shareit.request;

import lombok.experimental.PackagePrivate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.RequestDto;
import ru.practicum.shareit.request.model.Request;
import ru.practicum.shareit.request.service.RequestService;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@PackagePrivate
public class RequestController {

    final RequestService requestService;
    final String sharerId = "X-Sharer-User-Id";

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping
    public RequestDto addRequest(@RequestHeader(value = sharerId) Integer userId, @RequestBody RequestDto requestDto) {
        return requestService.addRequest(userId, requestDto);
    }

    @GetMapping
    public List<RequestDto> findUserRequests(@RequestHeader(value = sharerId) Integer userId) {
        return requestService.findUserRequests(userId);
    }

    @GetMapping("/all")
    public List<RequestDto> findAllRequests(@RequestHeader(value = sharerId) Integer userId, @RequestParam(defaultValue = "0") Integer from, @RequestParam(defaultValue = "10") Integer size) {
        return requestService.findAllRequests(from, size, userId);
    }

    @GetMapping("/{requestId}")
    public RequestDto findRequestById(@RequestHeader(value = sharerId) Integer userId, @PathVariable Integer requestId) {
        return requestService.findRequestById(userId, requestId);
    }

}
