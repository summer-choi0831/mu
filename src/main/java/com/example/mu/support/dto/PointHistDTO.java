package com.example.mu.support.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PointHistDTO {

    long id;
    long memberNo;
    BigDecimal amount;
    PointAccType pointAccType;
    LocalDateTime createDate;
    LocalDateTime modifiedDate;

    @Builder
    public PointHistDTO(long id, long memberNo, BigDecimal amount, PointAccType pointAccType, LocalDateTime createDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.memberNo = memberNo;
        this.amount = amount;
        this.pointAccType = pointAccType;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }
}
