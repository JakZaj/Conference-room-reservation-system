package com.example.Conference.room.reservation.system.controllers;

import com.example.Conference.room.reservation.system.entities.MyUser;
import com.example.Conference.room.reservation.system.entities.Reservation;
import com.example.Conference.room.reservation.system.models.reservation.ReservationRequest;
import com.example.Conference.room.reservation.system.models.reservation.ReservationResponse;
import com.example.Conference.room.reservation.system.models.reservation.UpdateReservationRequest;
import com.example.Conference.room.reservation.system.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/find-by-user/{userId}")
    public ResponseEntity<List<ReservationResponse>> getAllRoomsOwnedByUser(@PathVariable Long userId) {
        List<Reservation> reservations = reservationService.findAllUserRooms(new MyUser(userId));
        List<ReservationResponse> result = reservations.stream()
                .map(ReservationResponse::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ReservationResponse> bookRoom(@RequestBody ReservationRequest request, @AuthenticationPrincipal MyUser myUser) {
        Reservation reservation = reservationService.bookRoom(request.getRoomId(), myUser, request.getStartTime(), request.getEndTime());
        ReservationResponse reservationResponse = new ReservationResponse(reservation);
        return new ResponseEntity<>(reservationResponse, HttpStatus.OK);
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<String> updateReservation(@PathVariable Long reservationId, @RequestBody UpdateReservationRequest request) {
        MyUser auth = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser requestedUser = new MyUser();
        requestedUser.setId(auth.getId());
        requestedUser.setUserRole(auth.getUserRole());

        reservationService.updateReservation(reservationId, request.getStartTime(), request.getEndTime(), requestedUser);

        return new ResponseEntity<>("Reservation has been updated", HttpStatus.OK);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long reservationId) {
        MyUser auth = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser requestedUser = new MyUser();
        requestedUser.setId(auth.getId());
        requestedUser.setUserRole(auth.getUserRole());

        reservationService.deleteReservation(reservationId, requestedUser);
        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }
}