package com.example.Conference.room.reservation.system.services;

import com.example.Conference.room.reservation.system.entities.MyUser;
import com.example.Conference.room.reservation.system.enums.UserRole;
import com.example.Conference.room.reservation.system.exceptions.EmailAlreadyExistsException;
import com.example.Conference.room.reservation.system.exceptions.NotFoundException;
import com.example.Conference.room.reservation.system.exceptions.UsernameAlreadyExistsException;
import com.example.Conference.room.reservation.system.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserService {
    private final MyUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public MyUser register(String email, String nickname, String password){
        if (repository.findByEmail(email).isPresent())
            throw new EmailAlreadyExistsException();
        if (repository.findByNickname(nickname).isPresent())
            throw new UsernameAlreadyExistsException();

        String encodePassword = passwordEncoder.encode(password);
        MyUser myUser = new MyUser(null, email, nickname, encodePassword, UserRole.USER);
        return repository.save(myUser);
    }

    public MyUser getUser(String email, String password) {
        Optional<MyUser> user = repository.findByEmail(email);
        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword()))
            throw new NotFoundException("User not found");
        return user.get();
    }

    public List<MyUser> getAllUsers(){
        return repository.findAll();
    }


}