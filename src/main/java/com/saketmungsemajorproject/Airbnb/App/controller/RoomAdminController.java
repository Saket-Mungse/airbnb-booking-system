package com.saketmungsemajorproject.Airbnb.App.controller;

import com.saketmungsemajorproject.Airbnb.App.dto.RoomDto;
import com.saketmungsemajorproject.Airbnb.App.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/hotels/{hotelId}/rooms")
public class RoomAdminController {
    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDto> createNewRoom(@PathVariable Long hotelId, @RequestBody RoomDto roomDto){
        return new ResponseEntity<>(roomService.createNewRoom(hotelId, roomDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRoomsInHotel(@PathVariable Long hotelId) {
        return ResponseEntity.ok(roomService.getAllRoomsInHotel(hotelId));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.getRoomById(roomId)) ;
    }

//    @PutMapping("/{roomId}")
//    public ResponseEntity<RoomDto> updateExistingRoom(@PathVariable Long roomId, @RequestBody RoomDto roomDto) {
//        return ResponseEntity.ok(roomService.updateExistingRoom(roomId,roomDto));
//    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> softDeleteRoom(@PathVariable Long hotelId, @PathVariable Long roomId) {
        roomService.softDeleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<RoomDto> updateRoomById(@PathVariable Long hotelId, @PathVariable Long roomId,
                                                  @RequestBody RoomDto roomDto) {
        return ResponseEntity.ok(roomService.updateRoomById(hotelId, roomId, roomDto));
    }

}
