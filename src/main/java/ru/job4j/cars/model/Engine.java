package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

/**
 * Класс описывает модель данных - двигатель
 */
@Entity
@Table(name = "engines")
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class Engine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String index;
}
