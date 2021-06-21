package com.pzwpj.projekt.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1")
public class HealthCheck {

    @GetMapping("/health")
    public String health() {
        return new String("I'm fine!");
    }

    @GetMapping("/auth-health")
    public String authHealth() {
        return new String("I'm fine!");
    }

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
}
