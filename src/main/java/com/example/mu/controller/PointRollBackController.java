package com.example.mu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mu.service.RollBackService;
import com.example.mu.support.dto.ResponseContainer;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("rollback")
@RequiredArgsConstructor
public class PointRollBackController {

    private final RollBackService rollBackService;

    @ResponseBody
    @GetMapping
    public @NonNull ResponseContainer cancelUsePoint(@RequestParam Long pointHistId) throws Exception {
        rollBackService.rollbackPointAcc(pointHistId);
        return ResponseContainer.success();
    }

}
