package com.example.Conference.room.reservation.system.services;

import com.example.Conference.room.reservation.system.entities.MyUser;
import com.example.Conference.room.reservation.system.enums.UserRole;
import com.example.Conference.room.reservation.system.exceptions.EmailAlreadyExistsException;
import com.example.Conference.room.reservation.system.exceptions.UsernameAlreadyExistsException;
import com.example.Conference.room.reservation.system.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserService {
    private final MyUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public MyUser register(String email, String username, String password){
        if (repository.findByEmail(email).isPresent())
            throw new EmailAlreadyExistsException();
        if (repository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException();

        String encodePassword = passwordEncoder.encode(password);
        MyUser myUser = new MyUser(null, email, username, encodePassword, UserRole.USER);
        return repository.save(myUser);
    }


}