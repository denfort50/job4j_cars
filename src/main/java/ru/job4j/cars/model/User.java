package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

/**
 * Класс описывает модель данных - пользователь
 */
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    public String username;

    @NonNull
    private String login;

    @NonNull
    private String password;
}
