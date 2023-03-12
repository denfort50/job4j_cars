package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

/**
 * Класс описывает модель данных - водитель
 */
@Entity
@Table(name = "drivers")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private int id;

    @NonNull
    @EqualsAndHashCode.Include
    @Column(name = "name")
    private String name;

    @OneToOne
    @JoinColumn(name = "user_id")
    @NonNull
    private User user;
}
