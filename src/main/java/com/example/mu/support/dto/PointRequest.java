package com.example.mu.support.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PointRequest {
    long memberNo;
    BigDecimal amount;
    PointAccType pointAccType;
}
