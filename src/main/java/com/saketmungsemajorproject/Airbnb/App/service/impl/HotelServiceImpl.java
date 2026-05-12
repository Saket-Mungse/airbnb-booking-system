package com.saketmungsemajorproject.Airbnb.App.service.impl;

import com.saketmungsemajorproject.Airbnb.App.dto.HotelDto;
import com.saketmungsemajorproject.Airbnb.App.dto.HotelInfoDto;
import com.saketmungsemajorproject.Airbnb.App.dto.RoomDto;
import com.saketmungsemajorproject.Airbnb.App.entity.Hotel;
import com.saketmungsemajorproject.Airbnb.App.entity.Room;
import com.saketmungsemajorproject.Airbnb.App.exception.ResourceNotFoundException;
import com.saketmungsemajorproject.Airbnb.App.repository.HotelRepository;
import com.saketmungsemajorproject.Airbnb.App.repository.RoomRepository;
import com.saketmungsemajorproject.Airbnb.App.service.HotelService;
import com.saketmungsemajorproject.Airbnb.App.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        Hotel newHotel = modelMapper.map(hotelDto, Hotel.class);
        newHotel.setActive(false);//we are just added this hotel in our website, no one is booked until it now
        newHotel = hotelRepository.save(newHotel);
        return modelMapper.map(newHotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel with Id "+id+" not found"));
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateExistingHotel(Long id, HotelDto hotelDto) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel with Id "+id+" not found"));
        //The Hoteldto does not contain the id so it will become null
        //so we have to set the id before responding
        //if we do not set the id then it will return the id as null which throw the error
        modelMapper.map(hotelDto,hotel);
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public void softDeleteTheHotel(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel with Id "+id+" not found"));

        for(Room room:hotel.getRooms()){
            inventoryService.deleteAllInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void activateTheHotelById(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Hotel with Id "+id+" not found"));
        hotel.setActive(true);

        //assuming only do it once
        for(Room room:hotel.getRooms()){
            inventoryService.initializeRoomForAYear(room);
        }
    }

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(()->new ResourceNotFoundException("Hotel with Id "+hotelId+" not found"));
        List<RoomDto> rooms = hotel.getRooms()
                .stream().
                map((element) -> modelMapper.map(element, RoomDto.class))
                .toList();
        return new HotelInfoDto(modelMapper.map(hotel, HotelDto.class),rooms);
    }


}
