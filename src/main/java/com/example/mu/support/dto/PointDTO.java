package com.example.mu.support.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PointDTO {

    long id;
    long memberNo;
    BigDecimal amount;
    LocalDateTime expirationDateTime;

    LocalDateTime accDateTime;

    LocalDateTime lastUpdateDateTime;

    @Builder
    public PointDTO(long id, long memberNo, BigDecimal amount,
        LocalDateTime expirationDateTime, LocalDateTime accDateTime, LocalDateTime lastUpdateDateTime) {
        this.id = id;
        this.memberNo = memberNo;
        this.amount = amount;
        this.expirationDateTime = expirationDateTime;
        this.accDateTime = accDateTime;
        this.lastUpdateDateTime = lastUpdateDateTime;
    }
}
