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
 *
 * @author Denis Kalchenko
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
    @Column(name = "id")
    private int id;

    @NonNull
    @EqualsAndHashCode.Include
    @Column(name = "price")
    private int price;

    @NonNull
    @EqualsAndHashCode.Include
    @Column(name = "text")
    private String text;

    @EqualsAndHashCode.Include
    @Column(name = "created")
    private Timestamp created = Timestamp.valueOf(LocalDateTime.now());

    @EqualsAndHashCode.Include
    @Column(name = "status")
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
