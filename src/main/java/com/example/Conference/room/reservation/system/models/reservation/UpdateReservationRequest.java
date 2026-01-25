package com.example.Conference.room.reservation.system.models.reservation;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReservationRequest {
    @NotBlank
    private LocalDateTime startTime;

    @NotBlank
    private LocalDateTime endTime;
}