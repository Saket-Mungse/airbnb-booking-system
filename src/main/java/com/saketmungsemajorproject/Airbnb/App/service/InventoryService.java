package com.saketmungsemajorproject.Airbnb.App.service;

import com.saketmungsemajorproject.Airbnb.App.dto.HotelDto;
import com.saketmungsemajorproject.Airbnb.App.dto.HotelMinPriceDto;
import com.saketmungsemajorproject.Airbnb.App.dto.HotelSearchRequest;
import com.saketmungsemajorproject.Airbnb.App.entity.Room;
import org.springframework.data.domain.Page;


public interface InventoryService {
    void initializeRoomForAYear(Room room);
    void deleteAllInventories(Room room);

    Page<HotelMinPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
