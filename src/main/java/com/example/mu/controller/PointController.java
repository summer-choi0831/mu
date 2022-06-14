package com.example.mu.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mu.aggregate.PointAggregate;
import com.example.mu.entity.Point;
import com.example.mu.support.dto.PointAggDTO;
import com.example.mu.support.dto.PointDTO;
import com.example.mu.support.dto.PointRequest;
import com.example.mu.support.dto.ResponseContainer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("point")
@RequiredArgsConstructor
public class PointController {

    private final PointAggregate pointAggregate;

    @ResponseBody
    @PostMapping
    public @NonNull ResponseContainer pointRequest(@RequestBody PointRequest request) throws Exception {
        pointAggregate.sendRequest(request);
        return ResponseContainer.success();
    }
    @ResponseBody
    @GetMapping
    public @NonNull ResponseContainer findPoints(@RequestParam Long memberNo) {

        List<Point> byMember = pointAggregate.findPointBy(memberNo);
        List<PointDTO> pointDTOS = byMember.stream().map(Point::toDTO).collect(Collectors.toList());
        PointAggDTO pointAggDTO = PointAggDTO.builder().points(pointDTOS).totalAmount(
            pointDTOS.stream().map(PointDTO::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add)).build();;

        return ResponseContainer.success(pointAggDTO);
    }
}
