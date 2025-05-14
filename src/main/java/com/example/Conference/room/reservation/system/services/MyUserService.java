package com.example.Conference.room.reservation.system.services;

import com.example.Conference.room.reservation.system.entities.MyUser;
import com.example.Conference.room.reservation.system.entities.Reservation;
import com.example.Conference.room.reservation.system.entities.Room;
import com.example.Conference.room.reservation.system.enums.UserRole;
import com.example.Conference.room.reservation.system.exceptions.EmailAlreadyExistsException;
import com.example.Conference.room.reservation.system.repository.MyUserRepository;
import com.example.Conference.room.reservation.system.repository.ReservationRepository;
import com.example.Conference.room.reservation.system.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MyUserService {
    private final MyUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public MyUser register(String email, String userName, String password){
        if (repository.findByEmail(email).isPresent())
            throw new EmailAlreadyExistsException();
        String encodePassword = passwordEncoder.encode(password);
        MyUser myUser = new MyUser(null, email, userName, encodePassword, UserRole.USER);
        return repository.save(myUser);
    }

    public Reservation bookRoom(Long roomId, MyUser myUser, LocalDateTime startTime, LocalDateTime endTime) {
        List<Reservation> conflicts = reservationRepository.findByRoomIdAndStartTimeBetween(roomId, startTime, endTime);
        if (!conflicts.isEmpty()) {
            throw new IllegalStateException("Sala już zarezerwowana w tym czasie!");
        }
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new NoSuchElementException("Sala nie istnieje"));
        Reservation reservation = new Reservation(roomId, myUser, room, startTime, endTime);
        return reservationRepository.save(reservation);
    }
}