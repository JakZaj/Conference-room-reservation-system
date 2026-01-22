package com.example.Conference.room.reservation.system.controllers;

import com.example.Conference.room.reservation.system.entities.MyUser;
import com.example.Conference.room.reservation.system.entities.Reservation;
import com.example.Conference.room.reservation.system.models.reservation.ReservationRequest;
import com.example.Conference.room.reservation.system.models.reservation.ReservationResponse;
import com.example.Conference.room.reservation.system.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> bookRoom(@RequestBody ReservationRequest request, @AuthenticationPrincipal MyUser myUser) {
        Reservation reservation = reservationService.bookRoom(request.getRoomId(), myUser, request.getStartTime(), request.getEndTime());
        ReservationResponse reservationResponse = new ReservationResponse(reservation);
        return new ResponseEntity<>(reservationResponse, HttpStatus.OK);
    }
}