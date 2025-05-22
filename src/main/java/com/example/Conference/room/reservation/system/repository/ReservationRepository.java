package com.example.Conference.room.reservation.system.repository;

import com.example.Conference.room.reservation.system.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByRoomId(Long roomId);
}
