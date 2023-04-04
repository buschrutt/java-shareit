package ru.practicum.shareit.item.model;

import lombok.*;
import lombok.experimental.PackagePrivate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Table(name = "comments")
@Getter
@Setter
@ToString
@PackagePrivate
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "text")
    String text;

    @Column(name = "item_id")
    Integer item;

    @Column(name = "author_id")
    Integer authorName;

    @Column(name = "created")
    LocalDateTime created;
}
