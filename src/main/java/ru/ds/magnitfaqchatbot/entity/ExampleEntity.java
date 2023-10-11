package ru.ds.magnitfaqchatbot.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "example")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExampleEntity {

    @Id
    @SequenceGenerator(name = "example_id_seq", sequenceName = "example_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "example_id_seq")
    @Column(name = "id")
    Long id;

    @Column(name = "precondition")
    String precondition;

    @Column(name = "solution")
    String solution;
}
