package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "price_history")
@Data
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "before")
    private int before;

    @Column(name = "after")
    private int after;

    @Column(name = "created")
    private Timestamp created;
}
