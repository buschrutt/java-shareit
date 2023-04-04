package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build());
    }

    public ResponseEntity<Object> addItem(ItemDto itemDto, Integer ownerId) {

        return post("", ownerId, itemDto);
    }

    public ResponseEntity<Object> updateItem(Integer itemId, ItemDto itemDto, Integer ownerId) {

        return patch("/" + itemId, ownerId, itemDto);
    }

    public ResponseEntity<Object> getAllUserItems(Integer ownerId) {

        return get("", ownerId);
    }

    public ResponseEntity<Object> getItemById(Integer ownerId, Integer itemId) {

        return get("/" + ownerId, itemId);
    }

    public ResponseEntity<Object> getItemsSearched(String text) {

        return get("/search/?text=" + text);
    }

    public ResponseEntity<Object> addComment(CommentDto commentDto, long userId, long itemId) {

        return post("/" + itemId + "/comment", userId, null, commentDto);
    }


}
