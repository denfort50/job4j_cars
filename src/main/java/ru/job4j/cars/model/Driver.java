package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "driver")
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NonNull
    private String name;

    @OneToOne
    @JoinColumn(name = "user_id")
    @NonNull
    private User user;
}
