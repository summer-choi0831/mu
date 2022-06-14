package com.example.mu.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.example.mu.support.dto.PointDTO;
import com.example.mu.support.utils.LocalDateTimeUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "POINT")
@NoArgsConstructor
public class Point extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "point_seq")
    @SequenceGenerator(name = "point_seq")
    @Column(nullable = false)
    private Long id;
    @Column
    private long memberNo;
    @Column
    private BigDecimal amount;
    @Column
    private LocalDateTime expirationDateTime;

    @Builder
    public Point(Long id, long memberNo, BigDecimal amount, LocalDateTime expirationDateTime) {
        this.id = id;
        this.memberNo = memberNo;
        this.amount = amount;
        this.expirationDateTime = expirationDateTime;
    }
    public PointDTO toDTO() {
        return PointDTO.builder()
            .id(id)
            .memberNo(memberNo)
            .amount(amount)
            .expirationDateTime(expirationDateTime)
            .accDateTime(LocalDateTimeUtils.toLocalDateTime(getCreatedAt()))
            .lastUpdateDateTime(LocalDateTimeUtils.toLocalDateTime(getModifiedAt()))
            .build();
    }

    public void useAll() {
        amount = BigDecimal.ZERO;
    }

    public void use(BigDecimal usePoint) throws Exception {
        if (usePoint.compareTo(amount) > 0) {
            throw new Exception(String.format("Not enough points. amount = %f, usePoint = %f", amount, usePoint));
        }
        amount = amount.subtract(usePoint);
    }

    public void rollback(BigDecimal rollbackPoint) {
        amount = amount.add(rollbackPoint);
    }
}
