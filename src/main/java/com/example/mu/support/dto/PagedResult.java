package com.example.mu.support.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PagedResult<T> {

    public static <T> PagedResult<T> of(List<T> list, Pagenate page) {
        return new PagedResult<>(list, page);
    }

    private List<T> list;
    private Pagenate page;

}
