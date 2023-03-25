package ru.practicum.shareit.item;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemServiceImpl;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    final private ItemServiceImpl itemService;
    final private String epItemId = "/{itemId}";
    final private String sharerId = "X-Sharer-User-Id";

    public ItemController(ItemServiceImpl itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ItemDto addItem(@RequestHeader(value = sharerId) Integer ownerId, @RequestBody Item item) throws ValidationException, NotFoundException {
        return itemService.addItem(item, ownerId);
    }


    @PatchMapping(epItemId)
    public ItemDto updateItem(@RequestHeader(value = sharerId) Integer ownerId, @PathVariable Integer itemId, @RequestBody Item item) throws NotFoundException {
        return itemService.updateItem(itemId, item, ownerId);
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
    public CommentDto addComment(@RequestHeader(value = sharerId) Integer userId, @RequestBody Comment comment, @PathVariable Integer itemId) {
        return itemService.addComment(comment, userId, itemId);
    }

}
