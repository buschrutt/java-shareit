package ru.practicum.shareit.request;

import lombok.Data;
import lombok.experimental.PackagePrivate;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */

@Data
// @Builder
@PackagePrivate
public class ItemRequest {
    int id;
    String description;
    int requesterId;
    LocalDateTime requestCreation;
}
