package com.example.mu.support.dto;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class Pagenate {
    public static Pagenate of(Page page) {
        return builder()
                .number(page.getNumber())
                .pageSize(page.getSize())
                .totalCount(page.getTotalElements())
                .build();
    }

    private int number;
    private int pageSize;
    private long totalCount;
}
