package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.request.dto.RequestDto;

import static ru.practicum.shareit.Constants.HEADER;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class RequestController {

    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> addRequest(@RequestHeader(value = HEADER) Integer userId,
                                             @RequestBody RequestDto requestDto) throws ValidationException {
        if (requestDto.getDescription() == null) {
            throw new ValidationException("addRequest: ValidationException-- requestDto Description holds null ");
        }
        if (requestDto.getDescription().isEmpty()) {
            throw new ValidationException("addRequest: Description isEmpty");
        }
        return requestClient.addRequest(userId, requestDto);
    }

    @GetMapping
    public ResponseEntity<Object> findUserRequests(@RequestHeader(value = HEADER) Integer userId) {

        return requestClient.findUserRequests(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAllRequests(@RequestHeader(value = HEADER) Long userId,
                                            @RequestParam(defaultValue = "0") Integer from,
                                            @RequestParam(defaultValue = "10") Integer size) throws ValidationException {

        if (size == 0 || from < 0) {
            throw new ValidationException("findAllRequests: ValidationException-- RequestParam has invalid size or from values");
        }
        return requestClient.findAllRequests(from, size, userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> findRequestById(@RequestHeader(value = HEADER) Integer userId,
                                      @PathVariable Integer requestId) {

        return requestClient.findRequestById(userId, requestId);
    }


}
