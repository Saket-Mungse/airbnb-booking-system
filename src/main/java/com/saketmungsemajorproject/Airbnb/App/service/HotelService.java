package com.saketmungsemajorproject.Airbnb.App.service;

import com.saketmungsemajorproject.Airbnb.App.dto.HotelDto;
import com.saketmungsemajorproject.Airbnb.App.dto.HotelInfoDto;
import com.saketmungsemajorproject.Airbnb.App.entity.Hotel;

import java.util.List;

public interface HotelService {
    HotelDto createNewHotel(HotelDto hotelDto);
    HotelDto getHotelById(Long id);
    HotelDto updateExistingHotel(Long id, HotelDto hotelDto);
    void softDeleteTheHotel(Long id);
    void activateTheHotelById(Long id);

    HotelInfoDto getHotelInfoById(Long hotelId);

    List<HotelDto> getAllHotels();
}
