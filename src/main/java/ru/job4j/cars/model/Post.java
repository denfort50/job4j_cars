package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс описывает модель данных - объявление
 */
@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @NonNull
    @EqualsAndHashCode.Include
    private int price;

    @NonNull
    @EqualsAndHashCode.Include
    private String text;

    @EqualsAndHashCode.Include
    private Timestamp created = Timestamp.valueOf(LocalDateTime.now());

    @EqualsAndHashCode.Include
    private boolean status = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NonNull
    private User user;

    @ManyToMany(cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "participates",
            joinColumns = {
                    @JoinColumn(name = "post_id") },
            inverseJoinColumns = {
                    @JoinColumn(name = "user_id") }
    )
    private Set<User> participates = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<PriceHistory> priceHistoryList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    @NonNull
    private Car car;

    private byte[] photo;

    public void addToPriceHistoryList(PriceHistory priceHistory) {
        priceHistoryList.add(priceHistory);
    }

    public int getPriceHistoryListSize() {
        return priceHistoryList.size();
    }

    public int getPreviousPrice() {
        return priceHistoryList.get(priceHistoryList.size() - 1).getAfter();
    }
}
