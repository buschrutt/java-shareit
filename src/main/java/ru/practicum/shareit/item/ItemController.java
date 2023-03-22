package ru.practicum.shareit.item;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.error.NotFoundException;
import ru.practicum.shareit.error.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.item.repository.ItemRepository;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    final private ItemServiceImpl itemService;
    final private ItemRepository itemRepository;
    final private String epItemId = "/{itemId}";
    final private String sharerId = "X-Sharer-User-Id";

    public ItemController(ItemServiceImpl itemService, ItemRepository itemRepository) {
        this.itemService = itemService;
        this.itemRepository = itemRepository;
    }

    @PostMapping
    public ItemDto addItem(@RequestHeader(value = sharerId) Integer ownerId, @RequestBody Item item) throws ValidationException, NotFoundException {
        return itemService.addItem(item, ownerId, itemRepository);
    }


    @PatchMapping(epItemId)
    public ItemDto updateItem(@RequestHeader(value = sharerId) Integer ownerId, @PathVariable Integer itemId, @RequestBody Item item) throws NotFoundException {
        return itemService.updateItem(itemId, item, ownerId, itemRepository);
    }

    @GetMapping
    public List<ItemDto> getAllItems(@RequestHeader(value = sharerId) Integer ownerId) {
        return itemService.getAllItems(ownerId, itemRepository);
    }

    @GetMapping(epItemId)
    public ItemDto getItemById(@PathVariable Integer itemId) {
        return itemService.getItemById(itemId, itemRepository);
    }

    @GetMapping("/search")
    public List<ItemDto> getItemsSearched(@RequestParam String text) {
        return itemService.getItemsSearched(text, itemRepository);
    }

}
