package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.dto.LastOrNextBookingDto;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentDtoMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemMapperTests {

    @Test
    void toItemDtoMapperTest() {
        Item item = Item.builder()
                .id(1)
                .name("Item_Name")
                .description("Some_Item")
                .available(true)
                .requestId(3)
                .build();
        ItemDto itemDto = ItemDto.builder()
                .id(1)
                .name("Item_Name")
                .description("Some_Item")
                .available(true)
                .requestId(3)
                .build();
        assertEquals(ItemDtoMapper.toItemDto(item).toString(), itemDto.toString());
    }

    @Test
    void toItemWithBookingsAndCommentDtosMapperTest() {
        LocalDateTime timeNow = LocalDateTime.now();
        LocalDateTime timeStart = LocalDateTime.now();
        LocalDateTime timeEnd = LocalDateTime.now();
        Item item = Item.builder()
                .id(1)
                .name("Item_Name")
                .description("Some_Item")
                .available(true)
                .requestId(3)
                .build();
        CommentDto commentDto = CommentDto.builder()
                .id(1)
                .text("Some_text")
                .item(2)
                .authorName("Author")
                .created(timeNow)
                .build();
        List<CommentDto> itemComments = new ArrayList<>();
        itemComments.add(commentDto);
        LastOrNextBookingDto nextBookingDto = LastOrNextBookingDto.builder()
                .id(5)
                .start(timeStart)
                .end(timeEnd)
                .item(item)
                .bookerId(1)
                .status("APPROVED")
                .build();
        LastOrNextBookingDto lastBookingDto = LastOrNextBookingDto.builder()
                .id(4)
                .start(timeStart)
                .end(timeEnd)
                .item(item)
                .bookerId(8)
                .status("APPROVED")
                .build();
        ItemDto itemDto = ItemDto.builder()
                .id(1)
                .name("Item_Name")
                .description("Some_Item")
                .available(true)
                .lastBooking(lastBookingDto)
                .nextBooking(nextBookingDto)
                .comments(itemComments)
                .requestId(3)
                .build();
        assertEquals(ItemDtoMapper.toItemWithBookingsAndCommentDtos(item, itemComments, nextBookingDto, lastBookingDto).toString(), itemDto.toString());
    }

    @Test
    void toItemMapperTest() {
        Item item = Item.builder()
                .id(1)
                .name("Item_Name")
                .description("Some_Item")
                .available(true)
                .requestId(3)
                .ownerId(5)
                .build();
        ItemDto itemDto = ItemDto.builder()
                .id(1)
                .name("Item_Name")
                .description("Some_Item")
                .available(true)
                .requestId(3)
                .build();
        Item item0 = ItemDtoMapper.toItem(itemDto, 5);
        item0.setId(1);
        assertEquals(item0.toString(), item.toString());
    }

    @Test
    void toCommentDtoMapperTest() {
        LocalDateTime timeNow = LocalDateTime.now();
        CommentDto commentDto = CommentDto.builder()
                .id(1)
                .text("Some_text")
                .item(2)
                .authorName("Author")
                .created(timeNow)
                .build();
        Comment comment = Comment.builder()
                .id(1)
                .text("Some_text")
                .item(2)
                .authorName(9)
                .created(timeNow)
                .build();
        assertEquals(CommentDtoMapper.toCommentDto(comment, "Author").toString(), commentDto.toString());
    }

    @Test
    void toCommentMapperTest() {
        LocalDateTime timeNow = LocalDateTime.now();
        CommentDto commentDto = CommentDto.builder()
                .id(1)
                .text("Some_text")
                .item(2)
                .authorName("Author")
                .created(timeNow)
                .build();
        Comment comment = Comment.builder()
                .id(1)
                .text("Some_text")
                .item(2)
                .authorName(9)
                .created(timeNow)
                .build();
        Comment comment0 = CommentDtoMapper.toComment(commentDto, comment.getAuthorName());
        comment0.setId(1);
        assertEquals(comment0.toString(), comment.toString());
    }

}
