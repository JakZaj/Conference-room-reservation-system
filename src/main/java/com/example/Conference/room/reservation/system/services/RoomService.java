package com.example.Conference.room.reservation.system.services;

import com.example.Conference.room.reservation.system.entities.Room;
import com.example.Conference.room.reservation.system.exceptions.RoomAlreadyExistsException;
import com.example.Conference.room.reservation.system.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Room addRoom(String roomName, int roomCapacity, String roomLocation){
        if (roomRepository.findByNameAndLocation(roomName, roomLocation).isPresent())
            throw new RoomAlreadyExistsException();

        Room room = new Room(null, roomName, roomCapacity,roomLocation);
        return roomRepository.save(room);
    }
}
