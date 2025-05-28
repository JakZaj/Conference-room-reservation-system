package com.example.Conference.room.reservation.system.controllers;


import com.example.Conference.room.reservation.system.entities.Room;
import com.example.Conference.room.reservation.system.models.users.AddRoomRequest;
import com.example.Conference.room.reservation.system.models.users.RoomResponse;
import com.example.Conference.room.reservation.system.services.RoomService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/admin/rooms")
public class RoomController {

    private RoomService roomService;

    @PostMapping("/")
    public ResponseEntity<RoomResponse> addRoom(@Valid @RequestBody AddRoomRequest request) {
        Room room = roomService.addRoom(request.getName(), request.getCapacity(), request.getLocation());
        return new ResponseEntity<>(new RoomResponse("Successfully added room", room.getName(), room.getCapacity(), room.getLocation()), HttpStatus.OK);
    }
}
