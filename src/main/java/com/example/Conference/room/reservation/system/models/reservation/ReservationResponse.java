package com.example.Conference.room.reservation.system.models.reservation;

import com.example.Conference.room.reservation.system.entities.Reservation;
import com.example.Conference.room.reservation.system.models.users.MyUserDetailsResponse;
import com.example.Conference.room.reservation.system.models.users.RoomResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponse {
    public ReservationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.roomResponse = new RoomResponse(reservation.getRoom());
        this.startTime = reservation.getStartTime();
        this.endTime = reservation.getEndTime();
        this.user = new MyUserDetailsResponse(reservation.getMyUser());
    }

    private Long id;
    private RoomResponse roomResponse;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private MyUserDetailsResponse user;
}