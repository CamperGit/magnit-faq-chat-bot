package ru.ds.magnitfaqchatbot.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T> {

    private List<T> content;

    private Long totalElements;

    private int totalPages;

    private int pageSize;

    private int pageNumber;
}
