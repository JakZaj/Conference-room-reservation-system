package com.example.Conference.room.reservation.system.models.users;

import com.example.Conference.room.reservation.system.entities.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {

    public RoomResponse(Room room) {
        this.name = room.getName();
        this.capacity = room.getCapacity();
        this.location = room.getLocation();
        this.owner = new MyUserDetailsResponse(room.getOwner());
    }

    private String name;
    private int capacity;
    private String location;
    private MyUserDetailsResponse owner;
}
