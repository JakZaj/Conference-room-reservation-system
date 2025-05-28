package com.example.Conference.room.reservation.system.repository;

import com.example.Conference.room.reservation.system.entities.MyUser;
import com.example.Conference.room.reservation.system.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<MyUser> findByNameAndLocation(String roomName, String roomLocation);
}
