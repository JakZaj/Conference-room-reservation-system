package com.example.Conference.room.reservation.system.services;

import com.example.Conference.room.reservation.system.entities.MyUser;
import com.example.Conference.room.reservation.system.entities.Reservation;
import com.example.Conference.room.reservation.system.entities.Room;
import com.example.Conference.room.reservation.system.enums.UserRole;
import com.example.Conference.room.reservation.system.exceptions.*;
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

        isDateValid(startTime, endTime, listToCheckConflict);

        Room room = roomRepository.findById(roomId)
                .orElseThrow(RoomDoesNotExistsException::new);
        Reservation reservation = new Reservation(null, myUser, room, startTime, endTime);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> findAllUserRooms(MyUser myUser) {
        return reservationRepository.findByMyUser(myUser);
    }

    public void updateReservation(Long reservationId, LocalDateTime startTime, LocalDateTime endTime, MyUser requestedUser) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(NotFoundException::new);

        checkPermission(reservation, requestedUser);

        List<Reservation> listToCheckConflict = reservationRepository.findByRoomId(reservation.getRoom().getId());
        if (!listToCheckConflict.remove(reservation)) {
            throw new NotFoundException("Not found element in listToCheckConflict");
        }

        isDateValid(startTime, endTime, listToCheckConflict);

        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);

        reservationRepository.save(reservation);
    }

    public void deleteReservation(Long reservationId, MyUser requestedUser) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(NotFoundException::new);

        checkPermission(reservation, requestedUser);

        reservationRepository.delete(reservation);

    }

    private void checkPermission(Reservation reservation, MyUser requestedUser) {
        if (!reservation.getMyUser().getId().equals(requestedUser.getId()) && requestedUser.getUserRole() != UserRole.ADMIN) {
            throw new UnauthorizedActionException();
        }
    }

    private void isDateValid(LocalDateTime startTime, LocalDateTime endTime, List<Reservation> listToCheckConflict) {

        if (!(startTime.isBefore(endTime) && LocalDateTime.now().isBefore(startTime))) {
            throw new InvalidDataException();
        }

        if (isBookDateHaveNoConflicts(listToCheckConflict, startTime, endTime)) {
            throw new RoomAlreadyBookedException();
        }
    }

    private boolean isBookDateHaveNoConflicts(List<Reservation> listToCheckConflict, LocalDateTime startTime, LocalDateTime endTime) {
        for (var reservation : listToCheckConflict) {
            if (isStartTimeAndEndTimeAreBothBeforeOrAfterCheckedTime(reservation, startTime, endTime)) {
                continue;
            }
            return true;
        }
        return false;
    }

    private boolean isStartTimeAndEndTimeAreBothBeforeOrAfterCheckedTime(Reservation reservation, LocalDateTime startTime, LocalDateTime endTime) {
        return (startTime.isBefore(reservation.getStartTime()) && endTime.isBefore(reservation.getStartTime())) || (startTime.isAfter(reservation.getEndTime()) && endTime.isAfter(reservation.getEndTime()));
    }
}
