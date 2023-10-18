package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс описывает модель данных - кузов
 */
@Entity
@Table(name = "bodies")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class Body {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "name")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Body body = (Body) o;
        return id == body.id && name.equals(body.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
