package com.example.Conference.room.reservation.system.events;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest {

    @NotBlank
    private String name;
    @NotBlank
    private int capacity;
    @NotBlank
    private String location;
}
