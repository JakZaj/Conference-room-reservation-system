package com.example.Conference.room.reservation.system.repository;

import com.example.Conference.room.reservation.system.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByRoomIdAndStartTimeBetween(Long roomId, LocalDateTime start, LocalDateTime end);
}
