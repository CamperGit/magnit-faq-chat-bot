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
@Table(name = "faq")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FaqEntity {

    @Id
    @SequenceGenerator(name = "faq_id_seq", sequenceName = "faq_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faq_id_seq")
    @Column(name = "id")
    Long id;

    @Column(name = "title")
    String title;

    @Column(name = "question")
    String question;

    @Column(name = "answer")
    String answer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    CategoryEntity category;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "faq_id")
    List<AlgorithmEntity> algorithms;
}
