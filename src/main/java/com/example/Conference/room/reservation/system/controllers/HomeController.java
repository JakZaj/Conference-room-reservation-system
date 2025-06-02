package com.example.Conference.room.reservation.system.controllers;

import com.example.Conference.room.reservation.system.entities.MyUser;
import com.example.Conference.room.reservation.system.services.MyUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("user/test")
public class HomeController {

    private MyUserService myUserService;

    @GetMapping("/")
    public  ResponseEntity<List<MyUser>> home() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(myUserService.getAllUsers());
    }

    @GetMapping("/user")
    public String user() {
        return "Hello, User!";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Hello, Admin!";
    }
}
