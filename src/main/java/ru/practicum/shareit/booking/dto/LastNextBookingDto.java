package ru.practicum.shareit.booking.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.PackagePrivate;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Data
@Builder
@PackagePrivate
public class LastNextBookingDto {

    Integer id;
    LocalDateTime start;
    LocalDateTime end;
    Item item;
    Integer bookerId;
    String status;

}

