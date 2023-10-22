package ru.job4j.cars.model;

import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс описывает модель данных - кузов
 *
 * @author Denis Kalchenko
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "bodies")
public class Body {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
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
