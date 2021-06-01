package com.example.resilience4j.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class Controller
{
    @GetMapping("/defaultFallback")
    public String defaultMessage()
    {
        return "Service is unavailable. Please try again later.";
    }
}
