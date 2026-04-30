package com.practical.java.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @PostMapping("/register")
    public String registerUser(@RequestParam("name") String name, @RequestParam("email") String email) {
        return "User Registered: " + name + " | Email: " + email;
    }
}
