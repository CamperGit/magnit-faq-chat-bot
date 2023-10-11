package ru.ds.magnitfaqchatbot.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "algorithm")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AlgorithmEntity {

    @Id
    @SequenceGenerator(name = "algorithm_id_seq", sequenceName = "algorithm_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "algorithm_id_seq")
    @Column(name = "id")
    Long id;

    @Column(name = "title")
    String title;

    @Column(name = "text")
    String text;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "algorithm_id")
    List<ExampleEntity> examples;
}
