package com.example.mu.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mu.entity.PointHist;
import com.example.mu.support.dto.PointAccType;

public interface PointHistRepository extends JpaRepository<PointHist, Long> {
    Page<PointHist> findAllByMemberNoAndPointAccTypeNot(Long memberNo, Pageable pageable, PointAccType pointAccType);
}
