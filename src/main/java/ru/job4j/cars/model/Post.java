package ru.job4j.cars.model;

import lombok.NonNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Класс описывает модель данных - объявление
 *
 * @author Denis Kalchenko
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private int price;

    @NonNull
    private String text;

    private Timestamp created = Timestamp.valueOf(LocalDateTime.now());

    private boolean status = false;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NonNull
    private User user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "participates",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participates = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<PriceHistory> priceHistoryList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    @NonNull
    private Car car;

    @Column(name = "photo")
    private byte[] photo;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id && price == post.price && status == post.status
                && Objects.equals(text, post.text) && Objects.equals(created, post.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, text, created, status);
    }

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
