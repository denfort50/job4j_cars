package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

/**
 * Класс описывает модель данных - водитель
 */
@Entity
@Table(name = "drivers")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String name;

    @OneToOne
    @JoinColumn(name = "user_id")
    @NonNull
    private User user;
}
