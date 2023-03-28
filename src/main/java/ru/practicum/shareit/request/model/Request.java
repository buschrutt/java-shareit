package ru.practicum.shareit.request.model;

import lombok.*;
import lombok.experimental.PackagePrivate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */

@Entity
@Builder
@Table(name = "requests")
@Getter
@Setter
@ToString
@PackagePrivate
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "created")
    LocalDateTime created;

    @Column(name = "description")
    String description;

    @Column(name = "requester_id")
    Integer requesterId;
}