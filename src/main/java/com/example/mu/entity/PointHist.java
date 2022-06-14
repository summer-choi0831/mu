package com.example.mu.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.mu.support.dto.PointAccType;
import com.example.mu.support.dto.PointDTO;
import com.example.mu.support.dto.PointHistDTO;
import com.example.mu.support.utils.LocalDateTimeUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity(name = "POINT_HIST")
@NoArgsConstructor
public class PointHist extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column
    private long memberNo;

    @Column
    @Enumerated(EnumType.STRING)
    private PointAccType pointAccType;

    @Column
    private BigDecimal amount;

    @Setter
    @ManyToOne
    @JoinColumn(name = "point_id")
    private Point point;

    @Builder
    public PointHist(Long id, long memberNo, PointAccType pointAccType, BigDecimal amount) {
        this.id = id;
        this.memberNo = memberNo;
        this.pointAccType = pointAccType;
        this.amount = amount;
    }

    public void cancel(){
        pointAccType = PointAccType.CANCEL;
    }

    public PointHistDTO toDTO() {
        return PointHistDTO.builder()
            .id(id)
            .memberNo(memberNo)
            .amount(amount)
            .pointAccType(pointAccType)
            .createDate(LocalDateTimeUtils.toLocalDateTime(getCreatedAt()))
            .modifiedDate(LocalDateTimeUtils.toLocalDateTime(getModifiedAt()))
            .build();
    }
}
