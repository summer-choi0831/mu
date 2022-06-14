package com.example.mu.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mu.entity.Point;

public interface PointRepository extends JpaRepository<Point, Long> {

    List<Point>  findByMemberNo(Long memberNO);

    List<Point>  findByMemberNoAndExpirationDateTimeAfterOrderByExpirationDateTimeAsc(Long memberNO, LocalDateTime date);
}
