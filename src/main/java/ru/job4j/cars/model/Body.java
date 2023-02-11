package ru.job4j.cars.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

/**
 * Класс описывает модель данных - кузов
 */
@Entity
@Table(name = "bodies")
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class Body {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String name;
}
