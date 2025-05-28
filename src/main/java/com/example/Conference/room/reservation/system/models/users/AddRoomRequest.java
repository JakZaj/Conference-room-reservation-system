package com.example.Conference.room.reservation.system.models.users;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRoomRequest {

    @NotBlank
    private String name;

    @NotBlank
    private int capacity;

    @NotBlank
    private String location;
}
