package com.example.mu.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
public class RollBackService {
    private final PointRepository pointRepository;
    private final PointHistRepository pointHistRepository;

    @Transactional
    public void rollbackPointAcc(Long pointHistId) throws Exception {
        Optional<PointHist> histById = pointHistRepository.findById(pointHistId);
        PointHist pointHist = histById.orElseThrow(() -> new Exception("not found point hist. id = " + pointHistId));

        if (PointAccType.USE != pointHist.getPointAccType()) {
            throw new Exception("not found point hist. id = " + pointHistId);
        }

        Long pointId = pointHist.getPoint().getId();
        Optional<Point> byId = pointRepository.findById(pointId);
        Point point = byId.orElseThrow(() -> new Exception("not found point. id = " + pointId));

        point.rollback(pointHist.getAmount());
        pointHist.cancel();

        pointRepository.save(point);
        pointHistRepository.save(pointHist);

    }
}

