package com.example.Conference.room.reservation.system.controllers;


import com.example.Conference.room.reservation.system.entities.MyUser;
import com.example.Conference.room.reservation.system.entities.Room;
import com.example.Conference.room.reservation.system.models.users.AddRoomRequest;
import com.example.Conference.room.reservation.system.models.users.RoomResponse;
import com.example.Conference.room.reservation.system.services.RoomService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("/admin/rooms")
public class RoomController {

    private RoomService roomService;

    @PostMapping("/")
    public ResponseEntity<RoomResponse> addRoom(@Valid @RequestBody AddRoomRequest request) {

        MyUser auth = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser owner = new MyUser(auth.getId());

        Room room = roomService.addRoom(request.getName(), request.getCapacity(), request.getLocation(), owner);
        return new ResponseEntity<>(new RoomResponse(room), HttpStatus.OK);
    }


    @GetMapping("/find-by-user/{userId}")
    public ResponseEntity<List<RoomResponse>> getAllRoomsOwnedByUser(@PathVariable Long userId) {
        List<Room> rooms = roomService.findAllUserRooms(new MyUser(userId));
        List<RoomResponse> result = rooms.stream()
                .map(RoomResponse::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
