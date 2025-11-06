package com.example.Conference.room.reservation.system.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoomAlreadyExistsException extends RuntimeException {
    public RoomAlreadyExistsException() {
        super("Room is already exists in this location");
    }
}
