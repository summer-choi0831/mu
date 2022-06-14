package com.example.mu.aggregate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.mu.entity.Point;
import com.example.mu.entity.PointHist;
import com.example.mu.repository.PointHistRepository;
import com.example.mu.repository.PointRepository;
import com.example.mu.support.dto.PointAccType;
import com.example.mu.support.dto.PointRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointAggregate {
    private final PointRepository pointRepository;
    private final PointHistRepository pointHistRepository;

    public void sendRequest(PointRequest request) throws Exception {
        PointAccType pointAccType = request.getPointAccType();
        if (pointAccType == PointAccType.ACC) {
            accumulate(request.getMemberNo(), request.getAmount());
        } else if (pointAccType == PointAccType.USE) {
            use(request.getMemberNo(), request.getAmount());
        }
    }

    @Transactional
    public void use(Long memberNo, BigDecimal amount) throws Exception {
        List<Point> points = pointRepository.findByMemberNoAndExpirationDateTimeAfterOrderByExpirationDateTimeAsc(memberNo, LocalDateTime.now());

        useValidate(memberNo, amount, points);
        usePoint(amount, points);

        pointRepository.saveAll(points);
    }

    private void useValidate(Long memberNo, BigDecimal amount, List<Point> points) throws Exception {
        if (points.isEmpty()) {
            throw new Exception(String.format("There are no points. %.2f point, %d member", amount, memberNo));
        }

        BigDecimal sum = points.stream().map(Point::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (amount.compareTo(sum) >= 0) {
            throw new Exception(String.format("Not enough points. %.2f point, %d member", amount, memberNo));
        }
    }

    private void usePoint(BigDecimal useAmount, List<Point> points) throws Exception {
        BigDecimal accumulatedPoint = BigDecimal.ZERO;

        for (Point p : points) {
            accumulatedPoint = accumulatedPoint.add(p.getAmount());

            if (accumulatedPoint.compareTo(useAmount) >= 0) {
                p.use(useAmount);
                addHist(p, PointAccType.USE, useAmount);
                return;
            } else {
                p.useAll();
                addHist(p, PointAccType.USE, useAmount);
            }
        }
    }

    @Transactional
    public Point accumulate(Long memberNo, BigDecimal amount) {
        Point save = pointRepository.save(createPoint(memberNo, amount));
        addHist(save, PointAccType.ACC, save.getAmount());

        return save;
    }

    private Point createPoint(Long memberNo, BigDecimal amount) {
        return Point.builder()
            .memberNo(memberNo)
            .amount(amount)
            .expirationDateTime(getExpirationDateTime())
            .build();
    }

    private void addHist(Point point, PointAccType pointAccType, BigDecimal amount) {
        PointHist pointHist = PointHist.builder()
            .memberNo(point.getMemberNo())
            .pointAccType(pointAccType)
            .amount(amount)
            .build();

        pointHist.setPoint(point);
        pointHistRepository.save(pointHist);
    }

    /**
     * 적립된 포인트의 사용기간 구현 (1년)
     */
    private LocalDateTime getExpirationDateTime() {
        return LocalDateTime.now().plusYears(1);
    }

    public List<Point> findPointBy(Long memberNo) {
        return pointRepository.findByMemberNo(memberNo);
    }

    public Page<PointHist> findAllBy(Long memberNo, Pageable pageable) {
        return pointHistRepository.findAllByMemberNoAndPointAccTypeNot(memberNo, pageable, PointAccType.CANCEL);
    }

}

