package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "car")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NonNull
    private String name;

    @ManyToOne
    @JoinColumn(name = "engine_id", foreignKey = @ForeignKey(name = "ENGINE_ID_FK"))
    @NonNull
    private Engine engine;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "history_owner",
            joinColumns = {@JoinColumn(name = "car_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "driver_id", nullable = false, updatable = false)})
    private Set<Driver> drivers = new HashSet<>();
}
