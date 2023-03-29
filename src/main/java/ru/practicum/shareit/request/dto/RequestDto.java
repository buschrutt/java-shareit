package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.PackagePrivate;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;
import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */

@Data
@Builder
@PackagePrivate
public class RequestDto {

    Integer id;
    Integer requesterId;
    String description;
    LocalDateTime created;
    List<Item> items;
}
