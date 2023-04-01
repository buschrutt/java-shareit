package ru.practicum.shareit.item;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    final ItemService itemService;
    final String epItemId = "/{itemId}";
    final String sharerId = "X-Sharer-User-Id";

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ItemDto addItem(@RequestHeader(value = sharerId) Integer ownerId, @RequestBody ItemDto itemDto) throws ValidationException, NotFoundException {
        return itemService.addItem(itemDto, ownerId);
    }


    @PatchMapping(epItemId)
    public ItemDto updateItem(@RequestHeader(value = sharerId) Integer ownerId, @PathVariable Integer itemId, @RequestBody ItemDto itemDto) throws NotFoundException, ValidationException {
        return itemService.updateItem(itemId, itemDto, ownerId);
    }

    @GetMapping
    public List<ItemDto> getAllUserItems(@RequestHeader(value = sharerId) Integer ownerId) {
        return itemService.getAllUserItems(ownerId);
    }

    @GetMapping(epItemId)
    public ItemDto getItemById(@RequestHeader(value = sharerId) Integer ownerId, @PathVariable Integer itemId) throws NotFoundException {
        return itemService.getItemById(itemId, ownerId);
    }

    @GetMapping("/search")
    public List<ItemDto> getItemsSearched(@RequestParam String text) {
        return itemService.getItemsSearched(text);
    }

    @PostMapping(epItemId + "/comment")
    public CommentDto addComment(@RequestHeader(value = sharerId) Integer userId, @RequestBody CommentDto commentDto, @PathVariable Integer itemId) throws ValidationException {
        return itemService.addComment(commentDto, userId, itemId);
    }

}
