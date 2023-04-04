package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import static ru.practicum.shareit.Constants.HEADER;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {

    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> addItem(@RequestHeader(value = HEADER) Integer ownerId,
                                          @RequestBody ItemDto itemDto) {
        return itemClient.addItem(itemDto, ownerId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@RequestHeader(value = HEADER) Integer ownerId,
                              @PathVariable Integer itemId,
                              @RequestBody ItemDto itemDto) {
        return itemClient.updateItem(itemId, itemDto, ownerId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllUserItems(@RequestHeader(value = HEADER) Integer ownerId) {
        return itemClient.getAllUserItems(ownerId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(@RequestHeader(value = HEADER) Integer ownerId,
                               @PathVariable Integer itemId) {
        return itemClient.getItemById(itemId, ownerId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getItemsSearched(@RequestParam String text) {

        return itemClient.getItemsSearched(text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader(value = HEADER) long userId,
                                 @RequestBody CommentDto commentDto,
                                 @PathVariable long itemId) {
        return itemClient.addComment(commentDto, userId, itemId);
    }

}
