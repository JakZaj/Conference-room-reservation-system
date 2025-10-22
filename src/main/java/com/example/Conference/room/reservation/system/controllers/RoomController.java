package com.example.Conference.room.reservation.system.controllers;


import com.example.Conference.room.reservation.system.entities.MyUser;
import com.example.Conference.room.reservation.system.entities.Room;
import com.example.Conference.room.reservation.system.models.users.AddRoomRequest;
import com.example.Conference.room.reservation.system.models.users.PageResponse;
import com.example.Conference.room.reservation.system.models.users.RoomResponse;
import com.example.Conference.room.reservation.system.services.RoomService;
import com.example.Conference.room.reservation.system.events.UpdateEventRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/user/rooms")
public class RoomController {

    private RoomService roomService;

    @GetMapping("/list")
    public ResponseEntity<PageResponse<RoomResponse>> getRooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size,
            @RequestParam(required = false) String location){

        Pageable pageable = PageRequest.of(page, size);
        Page<Room> rooms = roomService.getRooms(pageable, location);
        Page<RoomResponse> responses = rooms.map(RoomResponse::new);

        PageResponse<RoomResponse> result = new PageResponse<>(
                responses.getContent(),
                responses.getNumber(),
                responses.getSize(),
                responses.getTotalElements(),
                responses.getTotalPages(),
                responses.isLast()
        );

        return ResponseEntity.ok(result);
    }

    @PostMapping("/")
    public ResponseEntity<RoomResponse> addRoom(@Valid @RequestBody AddRoomRequest request) {

        MyUser auth = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser owner = new MyUser(auth);

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

    @GetMapping("/find-room/{roomId}")
    public ResponseEntity<RoomResponse> getRoom(@PathVariable Long roomId) {
        Room room = roomService.getRoomById(roomId);
        RoomResponse roomResponse = new RoomResponse(room);
        return new ResponseEntity<>(roomResponse, HttpStatus.OK);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<String> updateRoom(@PathVariable Long eventId, @RequestBody UpdateEventRequest request) {
        MyUser auth = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser currentUser = new MyUser();
        currentUser.setId(auth.getId());
        currentUser.setUserRole(auth.getUserRole());

        roomService.updateEvent(eventId, request.getName(), request.getCapacity(), request.getLocation(), currentUser);

        return new ResponseEntity<>("Room has been updated", HttpStatus.OK);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long roomId) {
        MyUser auth = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MyUser currentUser = new MyUser();
        currentUser.setId(auth.getId());
        currentUser.setUserRole(auth.getUserRole());

        roomService.deleteRoom(roomId, currentUser);
        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }
}
