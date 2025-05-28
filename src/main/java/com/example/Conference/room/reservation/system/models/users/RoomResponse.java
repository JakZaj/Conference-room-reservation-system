package com.example.Conference.room.reservation.system.models.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    private String message;
    private String name;
    private int capacity;
    private String location;
}
