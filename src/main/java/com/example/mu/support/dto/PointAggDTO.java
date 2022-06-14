package com.example.mu.support.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PointAggDTO {
    List<PointDTO> points;
    BigDecimal totalAmount;

    @Builder
    public PointAggDTO(List<PointDTO> points, BigDecimal totalAmount) {
        this.points = points;
        this.totalAmount = totalAmount;
    }
}
