package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "auto_post")
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String text;

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
    private List<User> participates;
}
