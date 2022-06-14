package com.example.mu.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mu.aggregate.PointAggregate;
import com.example.mu.entity.PointHist;
import com.example.mu.support.dto.PagedResult;
import com.example.mu.support.dto.Pagenate;
import com.example.mu.support.dto.PointHistDTO;
import com.example.mu.support.dto.ResponseContainer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("point/history")
@RequiredArgsConstructor
public class PointHistController {

    private final PointAggregate pointAggregate;

    @ResponseBody
    @GetMapping
    public @NonNull ResponseContainer finePointHist(@RequestParam Long memberNo,
        @PageableDefault(page = 0, size = 5) Pageable pageable) {

        Page<PointHist> pointHistPage = pointAggregate.findAllBy(memberNo, pageable);
        List<PointHistDTO> pointHistDTOS = pointHistPage.stream()
            .map(PointHist::toDTO).collect(Collectors.toList());

        return ResponseContainer.success(PagedResult.of(pointHistDTOS, Pagenate.of(pointHistPage)));
    }
}
