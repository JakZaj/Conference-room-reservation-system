package com.example.Conference.room.reservation.system.services;

import com.example.Conference.room.reservation.system.entities.MyUser;
import com.example.Conference.room.reservation.system.entities.Room;
import com.example.Conference.room.reservation.system.exceptions.RoomAlreadyExistsException;
import com.example.Conference.room.reservation.system.exceptions.RoomDoesNotExistsException;
import com.example.Conference.room.reservation.system.exceptions.UnauthorizedActionException;
import com.example.Conference.room.reservation.system.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    public Page<Room> getRooms(Pageable pageable) {
//        Specification<RoomResponse> spec = createSpecification
        return roomRepository.findAll(pageable);
    }

//    private Specification<RoomResponse> createSpecification(Double latitude, Double longitude, Double distance, Boolean isActive, Boolean freePlaces) {
//        return (root, query, cb) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            if (latitude != null && longitude != null && distance != null) {
//                List<Location> locations = locationService.findLocationWithinDistance(latitude, longitude, distance);
//
//                predicates.add(root.get("location").in(locations));
//
//            }
//
//            if (isActive != null) {
//                predicates.add(cb.equal(root.get("isActive"), isActive));
//            }
//
//            if (freePlaces != null) {
//                predicates.add(cb.equal(root.get("freePlaces"), freePlaces));
//            }
//
//            return cb.and(predicates.toArray(new Predicate[0]));
//        };
//    }

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

    public void deleteRoom(Long roomId){
        Room room = roomRepository.findById(roomId).orElseThrow(RoomDoesNotExistsException::new);
        roomRepository.delete(room);
    }

    public void updateEvent(Long roomId, String name, int capacity, String location, MyUser myUser){

        Room existingRoom = getRoomById(roomId);

        if (!existingRoom.getOwner().getId().equals(myUser.getId())){
            throw new UnauthorizedActionException();
        }

        existingRoom.setName(name);
        existingRoom.setCapacity(capacity);
        existingRoom.setLocation(location);

        roomRepository.save(existingRoom);
    }
}
