package com.example.Conference.room.reservation.system.controllers;

import com.example.Conference.room.reservation.system.entities.MyUser;
import com.example.Conference.room.reservation.system.entities.Reservation;
import com.example.Conference.room.reservation.system.events.EventReservationRequest;
import com.example.Conference.room.reservation.system.services.ReservationService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Reservation> bookRoom(@RequestBody EventReservationRequest request, @AuthenticationPrincipal MyUser myUser) {
        Reservation reservation = reservationService.bookRoom(request.getRoomId(), myUser, request.getStartTime(), request.getEndTime());
        return ResponseEntity.ok(reservation);
    }
}