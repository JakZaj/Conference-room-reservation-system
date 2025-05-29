package com.example.Conference.room.reservation.system.controllers;

import com.example.Conference.room.reservation.system.config.JwtService;
import com.example.Conference.room.reservation.system.entities.MyUser;
import com.example.Conference.room.reservation.system.enums.UserRole;
import com.example.Conference.room.reservation.system.models.users.LoginRequest;
import com.example.Conference.room.reservation.system.models.users.MyUserDetailsResponse;
import com.example.Conference.room.reservation.system.models.users.RegisterRequest;
import com.example.Conference.room.reservation.system.models.users.RegisterResponse;
import com.example.Conference.room.reservation.system.services.MyUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/users")
public class MyUserController {

    final MyUserService myUserService;
    final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        MyUser myUser = myUserService.register(request.getEmail(), request.getUserName(), request.getPassword());
        return new ResponseEntity<>(new RegisterResponse("Successfully registered", myUser.getEmail(), myUser.getUsername(), UserRole.USER.toString()), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<MyUserDetailsResponse> login(@Valid @RequestBody final LoginRequest request){
        MyUser myUser = myUserService.getUser(request.getEmail(), request.getPassword());
        String token = jwtService.generateToken(myUser);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token);
        return new ResponseEntity<>(new MyUserDetailsResponse(myUser), httpHeaders, HttpStatus.OK);
    }


}
