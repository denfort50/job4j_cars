package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private int price;

    @NonNull
    private String text;

    @NonNull
    private Timestamp created;

    @NonNull
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "user_id")
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

    @OneToMany
    @JoinColumn(name = "post_id")
    private List<PriceHistory> priceHistory;

    @OneToOne
    @JoinColumn(name = "car_id")
    @NonNull
    private Car car;

    private byte[] photo;
}
