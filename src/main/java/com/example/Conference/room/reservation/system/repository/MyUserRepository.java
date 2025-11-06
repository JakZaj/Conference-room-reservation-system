package com.example.Conference.room.reservation.system.repository;

import com.example.Conference.room.reservation.system.entities.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByNickname(String nickname);
    Optional<MyUser> findByEmail(String email);
}
