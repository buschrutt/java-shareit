package ru.practicum.shareit.user.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.PackagePrivate;

import javax.persistence.*;

/**
 * TODO Sprint add-controllers.
 */

@Entity
@Table (name = "users")
@Getter @Setter @ToString
@PackagePrivate
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column (name = "name")
    String name;

    @Column (name = "email")
    String email;
}
