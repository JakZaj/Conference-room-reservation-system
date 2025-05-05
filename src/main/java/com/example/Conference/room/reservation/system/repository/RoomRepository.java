package com.example.Conference.room.reservation.system.repository;

import com.example.Conference.room.reservation.system.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
