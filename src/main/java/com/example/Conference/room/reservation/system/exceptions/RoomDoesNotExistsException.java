package com.example.Conference.room.reservation.system.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoomDoesNotExistsException extends RuntimeException {
    public RoomDoesNotExistsException() {
      super("Room id does not exist");
    }
}
