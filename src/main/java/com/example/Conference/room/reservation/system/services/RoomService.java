package com.example.Conference.room.reservation.system.services;

import com.example.Conference.room.reservation.system.entities.MyUser;
import com.example.Conference.room.reservation.system.entities.Room;
import com.example.Conference.room.reservation.system.exceptions.RoomAlreadyExistsException;
import com.example.Conference.room.reservation.system.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Room addRoom(String roomName, int roomCapacity, String roomLocation, MyUser owner){
        if (roomRepository.findByNameAndLocation(roomName, roomLocation).isPresent())
            throw new RoomAlreadyExistsException();

        Room room = new Room(null, roomName, roomCapacity, roomLocation, owner);
        return roomRepository.save(room);
    }

    public List<Room> findAllUserRooms(MyUser myUser){
        return roomRepository.findByOwner(myUser);
    }
}
