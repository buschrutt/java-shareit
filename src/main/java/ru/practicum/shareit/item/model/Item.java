package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.experimental.PackagePrivate;

/**
 * TODO Sprint add-controllers.
 */

@Data
// @Builder
@PackagePrivate
public class Item {
    int id;
    String name;
    String description;
    Boolean available;
    Integer ownerId;
    String request; // if was created by user request - holds the link to that request
}
