package ru.job4j.cars.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Класс описывает модель данных - история цен
 *
 * @author Denis Kalchenko
 */
@Entity
@Getter
@Setter
@Table(name = "price_history")
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int before;

    private int after;

    private Timestamp created;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PriceHistory that = (PriceHistory) o;
        return id == that.id && before == that.before && after == that.after && Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, before, after, created);
    }
}
