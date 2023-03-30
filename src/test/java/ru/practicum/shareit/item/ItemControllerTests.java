package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = ItemController.class)
public class ItemControllerTests {

    @Autowired
    ObjectMapper mapper;
    @MockBean
    ItemController itemController;
    @Autowired
    private MockMvc mvc;
    final String header = "X-Sharer-User-Id";
    private final ItemDto itemDto = ItemDto.builder()
            .name("ItemName")
            .description("description")
            .available(true)
            .requestId(1)
            .ownerId(2)
            .build();
    private final ItemDto itemDto0 = ItemDto.builder()
            .name("ItemName0")
            .description("description0")
            .available(true)
            .requestId(3)
            .ownerId(2)
            .build();
    private final CommentDto commentDto = CommentDto.builder()
            .text("Comment")
            .authorName("authorName")
            .item(1)
            .created(LocalDateTime.now())
            .build();

    @SneakyThrows
    @Test
    void addItemControllerTest() {
        when(itemController.addItem(any(), any())).thenReturn(itemDto);
        mvc.perform(post("/items")
                        .content(mapper.writeValueAsString(itemDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(header, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Integer.class))
                .andExpect(jsonPath("$.description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDto.getAvailable()), Boolean.class))
                .andExpect(jsonPath("$.requestId", is(1)))
                .andExpect(jsonPath("$.ownerId", is(2)));
    }

    @SneakyThrows
    @Test
    void updateItemControllerTest() {
        Integer itemId = 1;
        when(itemController.updateItem(any(), any(), any())).thenReturn(itemDto);
        mvc.perform(patch("/items/{itemId}", itemId)
                        .content(mapper.writeValueAsString(itemDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(header, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Integer.class))
                .andExpect(jsonPath("$.description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDto.getAvailable()), Boolean.class))
                .andExpect(jsonPath("$.requestId", is(1)))
                .andExpect(jsonPath("$.ownerId", is(2)));
    }

    @SneakyThrows
    @Test
    void getAllUserItemsControllerTest() {
        List<ItemDto> ItemDtoList = new ArrayList<>();
        ItemDtoList.add(itemDto);
        ItemDtoList.add(itemDto0);
        when(itemController.getAllUserItems(any())).thenReturn(ItemDtoList);
        mvc.perform(get("/items")
                        .content(mapper.writeValueAsString(ItemDtoList))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(header, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(itemDto.getId()), Integer.class))
                .andExpect(jsonPath("$[0].description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$[0].available", is(itemDto.getAvailable()), Boolean.class))
                .andExpect(jsonPath("$[0].requestId", is(1)))
                .andExpect(jsonPath("$[0].ownerId", is(2)))
                .andExpect(jsonPath("$[1].id", is(itemDto0.getId()), Integer.class))
                .andExpect(jsonPath("$[1].description", is(itemDto0.getDescription())))
                .andExpect(jsonPath("$[1].available", is(itemDto0.getAvailable()), Boolean.class))
                .andExpect(jsonPath("$[1].requestId", is(3)))
                .andExpect(jsonPath("$[1].ownerId", is(2)));
    }

    @SneakyThrows
    @Test
    void getItemByIdControllerTest() {
        Integer sharerId = 1;
        when(itemController.getItemById(any(), any())).thenReturn(itemDto);
        mvc.perform(get("/items/{sharerId}", sharerId)
                        .content(mapper.writeValueAsString(itemDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(header, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Integer.class))
                .andExpect(jsonPath("$.description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDto.getAvailable()), Boolean.class))
                .andExpect(jsonPath("$.requestId", is(1)))
                .andExpect(jsonPath("$.ownerId", is(2)));
    }

    @SneakyThrows
    @Test
    void getItemsSearchedControllerTest() {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("text", "description");
        List<ItemDto> ItemDtoList = new ArrayList<>();
        ItemDtoList.add(itemDto);
        ItemDtoList.add(itemDto0);
        when(itemController.getItemsSearched(any())).thenReturn(ItemDtoList);
        mvc.perform(get("/items/search")
                        .params(requestParams)
                        .content(mapper.writeValueAsString(ItemDtoList))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(header, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(itemDto.getId()), Integer.class))
                .andExpect(jsonPath("$[0].description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$[0].available", is(itemDto.getAvailable()), Boolean.class))
                .andExpect(jsonPath("$[0].requestId", is(1)))
                .andExpect(jsonPath("$[0].ownerId", is(2)))
                .andExpect(jsonPath("$[1].id", is(itemDto0.getId()), Integer.class))
                .andExpect(jsonPath("$[1].description", is(itemDto0.getDescription())))
                .andExpect(jsonPath("$[1].available", is(itemDto0.getAvailable()), Boolean.class))
                .andExpect(jsonPath("$[1].requestId", is(3)))
                .andExpect(jsonPath("$[1].ownerId", is(2)));
    }

    @SneakyThrows
    @Test
    void addCommentControllerTest() {
        Integer itemId = 1;
        when(itemController.addComment(any(), any(), any())).thenReturn(commentDto);
        mvc.perform(post("/items/{itemId}/comment", itemId)
                        .content(mapper.writeValueAsString(itemDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(header, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(commentDto.getId()), Integer.class))
                .andExpect(jsonPath("$.text", is(commentDto.getText())))
                .andExpect(jsonPath("$.authorName", is(commentDto.getAuthorName())))
                .andExpect(jsonPath("$.item", is(1)));
    }

}
