package ru.practicum.shareit.item.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.PackagePrivate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@ToString
@PackagePrivate
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "text")
    String text;

    @Column(name = "item_id")
    int item;

    @Column(name = "author_id")
    String author;

    @Column(name = "created")
    LocalDateTime created;
}
