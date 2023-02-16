package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс описывает модель данных - автомобиль
 */
@Entity
@Table(name = "cars")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @NonNull
    @EqualsAndHashCode.Include
    private String brand;

    @NonNull
    @EqualsAndHashCode.Include
    private String model;

    @ManyToOne
    @JoinColumn(name = "body_id")
    @NonNull
    private Body body;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "engine_id")
    @NonNull
    private Engine engine;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "history_owners",
            joinColumns = {@JoinColumn(name = "car_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "driver_id", nullable = false, updatable = false)})
    private Set<Driver> drivers = new HashSet<>();
}
