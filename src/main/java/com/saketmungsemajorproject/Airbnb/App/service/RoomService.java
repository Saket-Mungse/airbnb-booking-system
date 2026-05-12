package com.saketmungsemajorproject.Airbnb.App.service;

import com.saketmungsemajorproject.Airbnb.App.dto.RoomDto;

import java.util.List;

public interface RoomService {
    RoomDto createNewRoom(Long hotelId, RoomDto roomDto);
    RoomDto updateExistingRoom(Long id, RoomDto roomDto);
    RoomDto getRoomById(Long id);
    List<RoomDto> getAllRoomsInHotel(Long hotelId);
    void softDeleteRoom(Long id);
}
