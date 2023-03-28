package ru.practicum.shareit.request.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.PackagePrivate;

import java.time.LocalDateTime;

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
}
