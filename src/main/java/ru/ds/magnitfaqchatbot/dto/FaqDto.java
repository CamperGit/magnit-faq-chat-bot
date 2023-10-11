package ru.ds.magnitfaqchatbot.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FaqDto {

    Long id;

    String title;

    String question;

    String answer;

    CategoryDto category;

    List<AlgorithmDto> algorithms;
}
