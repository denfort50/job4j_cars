package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auto_post")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "text")
    @NonNull
    private String text;

    @Column(name = "created")
    @NonNull
    private Timestamp created;

    @ManyToOne
    @JoinColumn(name = "auto_user_id")
    @NonNull
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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<PriceHistory> priceHistory;

    @OneToOne
    @JoinColumn(name = "car_id")
    @NonNull
    private Car car;

    @Column(name = "photo")
    private byte[] photo;
}
