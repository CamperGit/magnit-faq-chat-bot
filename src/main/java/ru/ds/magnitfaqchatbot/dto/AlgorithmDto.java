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
public class AlgorithmDto {

    Long id;

    String title;

    String text;

    List<ExampleDto> examples;
}
