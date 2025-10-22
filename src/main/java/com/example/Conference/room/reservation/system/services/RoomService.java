package com.example.Conference.room.reservation.system.services;

import com.example.Conference.room.reservation.system.entities.MyUser;
import com.example.Conference.room.reservation.system.entities.Room;
import com.example.Conference.room.reservation.system.enums.UserRole;
import com.example.Conference.room.reservation.system.exceptions.RoomAlreadyExistsException;
import com.example.Conference.room.reservation.system.exceptions.RoomDoesNotExistsException;
import com.example.Conference.room.reservation.system.exceptions.UnauthorizedActionException;
import com.example.Conference.room.reservation.system.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Page<Room> getRooms(Pageable pageable, String location) {
        Specification<Room> spec = createSpecification(location);
        return roomRepository.findAll(spec, pageable);
    }

    private Specification<Room> createSpecification(String location) {
        return (root, query, cb) -> {
            if (location == null || location.isBlank()) {
                return cb.conjunction();
            }

            return cb.equal(root.get("location"), location);
        };
    }

    public Room addRoom(String roomName, int roomCapacity, String roomLocation, MyUser owner){
        if (roomRepository.findByNameAndLocation(roomName, roomLocation).isPresent())
            throw new RoomAlreadyExistsException();

        Room room = new Room(null, roomName, roomCapacity, roomLocation, owner);
        return roomRepository.save(room);
    }

    public List<Room> findAllUserRooms(MyUser myUser){
        return roomRepository.findByOwner(myUser);
    }

    public Room getRoomById(Long roomId){
        return roomRepository.findById(roomId).orElseThrow(RoomDoesNotExistsException::new);
    }

    public void deleteRoom(Long roomId, MyUser currentUser){
        Room room = roomRepository.findById(roomId).orElseThrow(RoomDoesNotExistsException::new);

        if (!room.getOwner().getId().equals(currentUser.getId()) && currentUser.getUserRole() != UserRole.ADMIN){
            throw new UnauthorizedActionException();
        }

        roomRepository.delete(room);
    }

    public void updateEvent(Long roomId, String name, int capacity, String location, MyUser currentUser){

        Room existingRoom = getRoomById(roomId);

        if (!existingRoom.getOwner().getId().equals(currentUser.getId()) && currentUser.getUserRole() != UserRole.ADMIN){
            throw new UnauthorizedActionException();
        }

        existingRoom.setName(name);
        existingRoom.setCapacity(capacity);
        existingRoom.setLocation(location);

        roomRepository.save(existingRoom);
    }
}
