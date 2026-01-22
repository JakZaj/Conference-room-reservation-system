package com.example.Conference.room.reservation.system.services;

import com.example.Conference.room.reservation.system.entities.MyUser;
import com.example.Conference.room.reservation.system.entities.Reservation;
import com.example.Conference.room.reservation.system.entities.Room;
import com.example.Conference.room.reservation.system.exceptions.InvalidDataException;
import com.example.Conference.room.reservation.system.exceptions.RoomAlreadyBookedException;
import com.example.Conference.room.reservation.system.exceptions.RoomDoesNotExistsException;
import com.example.Conference.room.reservation.system.repository.MyUserRepository;
import com.example.Conference.room.reservation.system.repository.ReservationRepository;
import com.example.Conference.room.reservation.system.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;
    private final MyUserRepository myUserRepository;

    public Reservation bookRoom(Long roomId, MyUser myUser, LocalDateTime startTime, LocalDateTime endTime) {
        List<Reservation> listToCheckConflict = reservationRepository.findByRoomId(roomId);

        if (!isDateValid(startTime, endTime))
            throw new InvalidDataException();

        if (isBookDateHaveNoConflicts(listToCheckConflict, startTime, endTime)) {
            throw new RoomAlreadyBookedException();
        }

        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomDoesNotExistsException::new);
        Reservation reservation = new Reservation(null, myUser, room, startTime, endTime);
        return reservationRepository.save(reservation);
    }

    private boolean isDateValid(LocalDateTime startTime, LocalDateTime endTime){
        return startTime.isBefore(endTime) && LocalDateTime.now().isBefore(startTime);
    }

    private boolean isBookDateHaveNoConflicts(List<Reservation> listToCheckConflict, LocalDateTime startTime, LocalDateTime endTime){
        for (var reservation : listToCheckConflict){
           if (isStartTimeAndEndTimeAreBothBeforeOrAfterCheckedTime(reservation, startTime, endTime)){
               continue;
           }
           return true;
        }
        return false;
    }

    private boolean isStartTimeAndEndTimeAreBothBeforeOrAfterCheckedTime(Reservation reservation, LocalDateTime startTime, LocalDateTime endTime){
        return (startTime.isBefore(reservation.getStartTime()) && endTime.isBefore(reservation.getStartTime())) || (startTime.isAfter(reservation.getEndTime()) && endTime.isAfter(reservation.getEndTime()));
    }
}
