package ru.ds.magnitfaqchatbot.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExampleDto {

    Long id;

    String precondition;

    String solution;
}
