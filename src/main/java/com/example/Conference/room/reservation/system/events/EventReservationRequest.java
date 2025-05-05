package com.example.Conference.room.reservation.system.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventReservationRequest {
    private Long roomId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
