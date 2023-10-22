package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

/**
 * Класс описывает модель данных - двигатель
 *
 * @author Denis Kalchenko
 */
@Entity
@Table(name = "engines")
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class Engine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "index")
    private String index;
}
