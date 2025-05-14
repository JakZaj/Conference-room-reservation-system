package com.example.Conference.room.reservation.system.controllers;

import com.example.Conference.room.reservation.system.entities.MyUser;
import com.example.Conference.room.reservation.system.enums.UserRole;
import com.example.Conference.room.reservation.system.models.users.RegisterRequest;
import com.example.Conference.room.reservation.system.models.users.RegisterResponse;
import com.example.Conference.room.reservation.system.repository.MyUserRepository;
import com.example.Conference.room.reservation.system.services.MyUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/users")
public class MyUserController {

    private MyUserService myUserService;

    @PostMapping("/register")
    public  ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        MyUser myUser = myUserService.register(request.getEmail(), request.getUserName(), request.getPassword());
        return new ResponseEntity<>(new RegisterResponse("Successfully registered", myUser.getEmail(), myUser.getUsername(), UserRole.USER.toString()), HttpStatus.OK);
    }

}
