package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "auto_user")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "login")
    @NonNull
    private String login;

    @Column(name = "password")
    @NonNull
    private String password;
}
