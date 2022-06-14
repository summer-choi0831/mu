package com.example.mu.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mu.support.dto.ResponseContainer;

import lombok.NonNull;

@RestController
public class HelloController {

    @ResponseBody
    @GetMapping("hello")
    public @NonNull ResponseContainer hello() {
        return ResponseContainer.success("hello " + LocalDateTime.now());
    }
}
