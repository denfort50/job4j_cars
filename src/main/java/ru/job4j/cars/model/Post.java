package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auto_post")
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "text")
    private String text;

    @Column(name = "created")
    private Timestamp created;

    @ManyToOne
    @JoinColumn(name = "auto_user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "participates",
            joinColumns = {
                    @JoinColumn(name = "post_id") },
            inverseJoinColumns = {
                    @JoinColumn(name = "user_id") }
    )
    private List<User> participates = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(name = "photo")
    private byte[] photo;
}
