package ru.practicum.shareit.item.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.PackagePrivate;

import javax.persistence.*;

/**
 * TODO Sprint add-controllers.
 */

@Entity
@Table(name = "items")
@Getter @Setter @ToString
@PackagePrivate
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "is_available")
    Boolean available;

    @Column(name = "owner_id")
    Integer ownerId;

    @Column(name = "request_id")
    Integer request; // if was created by user request - holds the link to that request
}
