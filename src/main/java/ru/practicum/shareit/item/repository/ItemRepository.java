package ru.practicum.shareit.item.repository;

import lombok.Data;
import lombok.experimental.PackagePrivate;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.Map;

@Data
// @Builder
@PackagePrivate
@Repository
public class ItemRepository {
    final Map<Integer, Item> items = new HashMap<>();
    Integer currentItemId = 0;

    public Integer RenewCurrentItemId() {
        return ++currentItemId;
    }
}
