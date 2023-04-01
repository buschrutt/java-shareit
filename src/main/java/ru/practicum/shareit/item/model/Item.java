package ru.practicum.shareit.item.model;

import lombok.*;
import lombok.experimental.PackagePrivate;

import javax.persistence.*;

/**
 * TODO Sprint add-controllers.
 */

@Entity
@Builder
@Table(name = "items")
@Getter
@Setter
@ToString
@PackagePrivate
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "is_available")
    Boolean available;

    @Column(name = "owner_id")
    Integer ownerId;

    @Column(name = "request_id")
    Integer requestId; // if was created by user request - holds the link to that request
}
