package ru.job4j.cars.model;

import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Класс описывает модель данных - автомобиль
 *
 * @author Denis Kalchenko
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String brand;

    @NonNull
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
            joinColumns = @JoinColumn(name = "car_id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "driver_id", nullable = false, updatable = false))
    private Set<Driver> drivers = new HashSet<>();

    @OneToOne(mappedBy = "car")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Post post;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return id == car.id && Objects.equals(brand, car.brand) && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model);
    }
}
