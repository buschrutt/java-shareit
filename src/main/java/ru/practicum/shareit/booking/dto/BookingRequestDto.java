package ru.practicum.shareit.booking.dto;

import lombok.*;
import lombok.experimental.PackagePrivate;

import java.time.LocalDateTime;

@Data
@Builder
@PackagePrivate
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDto {
    Integer itemId;
    LocalDateTime start;
    LocalDateTime end;
}
