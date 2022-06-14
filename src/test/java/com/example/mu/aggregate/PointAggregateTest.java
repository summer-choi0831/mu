package com.example.mu.aggregate;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mu.entity.Point;

@SpringBootTest
class PointAggregateTest {

    @Autowired
    PointAggregate pointAggregate;

    @Test
    @DisplayName("적립-사용시나리오 30 적립, 20적립, 15사용, 남음 금액 35")
    void test1() throws Exception {
        Long memberNo = 1001L;
        pointAggregate.accumulate(memberNo, BigDecimal.valueOf(30));
        pointAggregate.accumulate(memberNo, BigDecimal.valueOf(20));
        pointAggregate.use(memberNo, BigDecimal.valueOf(15L));
        List<Point> points = pointAggregate.findPointBy(1001L);

        assertThat(points.size()).isEqualTo(2L);
        assertThat(points.stream().map(Point::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add).toString()).isEqualTo("35.00");
    }

}