package com.saketmungsemajorproject.Airbnb.App.service.impl;

import com.saketmungsemajorproject.Airbnb.App.dto.RoomDto;
import com.saketmungsemajorproject.Airbnb.App.entity.Hotel;
import com.saketmungsemajorproject.Airbnb.App.entity.Room;
import com.saketmungsemajorproject.Airbnb.App.exception.ResourceNotFoundException;
import com.saketmungsemajorproject.Airbnb.App.repository.HotelRepository;
import com.saketmungsemajorproject.Airbnb.App.repository.RoomRepository;
import com.saketmungsemajorproject.Airbnb.App.service.InventoryService;
import com.saketmungsemajorproject.Airbnb.App.service.RoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;

    @Override
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel with Id "+hotelId+" not found"));
        Room newRoom =  modelMapper.map(roomDto, Room.class);
        newRoom.setHotel(hotel);
        newRoom = roomRepository.save(newRoom);

        if(hotel.getActive()){
            inventoryService.initializeRoomForAYear(newRoom);
        }

        return modelMapper.map(newRoom,RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel with Id "+hotelId+" not found"));
//        List<Room> rooms = roomRepository.findAllRoomsByHotelId(hotelId);
//        List<RoomDto> ans = rooms
//                .stream().map((element) -> modelMapper.map(element, RoomDto.class))
//                .collect(Collectors.toList());
        //Why we remove above code, because we have ManyToOne mapping in Room class with Hotel;s
        //So we can create a OneToMany mapping of Hotel With Room in Hotel class
        //So we get directly all the list of rooms in that hotel
        return hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long id) {
        Room room = roomRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Room with Id "+id+" not found"));

        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public RoomDto updateExistingRoom(Long id, RoomDto roomDto) {
        Room room = roomRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Room with Id "+id+" not found"));
        modelMapper.map(roomDto,room);
        room.setId(id);
        room = roomRepository.save(room);
        return modelMapper.map(room, RoomDto.class);
    }

    @Transactional
    @Override
    public void softDeleteRoom(Long id) {
        Room room = roomRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Room with Id "+id+" not found"));

        inventoryService.deleteAllInventories(room);
        roomRepository.deleteById(id);
    }
}
