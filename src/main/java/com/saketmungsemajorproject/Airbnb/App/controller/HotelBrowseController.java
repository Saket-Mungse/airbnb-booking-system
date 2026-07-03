package com.saketmungsemajorproject.Airbnb.App.controller;

import com.saketmungsemajorproject.Airbnb.App.dto.HotelInfoDto;
import com.saketmungsemajorproject.Airbnb.App.dto.HotelMinPriceDto;
import com.saketmungsemajorproject.Airbnb.App.dto.HotelSearchRequest;
import com.saketmungsemajorproject.Airbnb.App.dto.HotelDto;
import com.saketmungsemajorproject.Airbnb.App.service.HotelService;
import com.saketmungsemajorproject.Airbnb.App.service.InventoryService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelBrowseController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;

    //Criteria for Search Hotels
    //We will perform this thing on inventory database
    //1)startDate<=date<=endDate
    //2)city should be match
    //3)availability: (totalCount-BookedCount)>=roomsCount
    //4)Room should not closed(closed==false)
    //5)Group the response by room(the single room should satisfy the 4 conditions)
    //6)Get the response by unique hotels

    @GetMapping("/search")
    public ResponseEntity<Page<HotelMinPriceDto>> searchHotels(@RequestBody HotelSearchRequest hotelSearchRequest){
        Page<HotelMinPriceDto> page = inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }

    //We add the same search hotel function as the upper
    //The reason is because in the frontend the axios is dropping the get request body so we are not able to see the body in frontend
    //But in the postman it is totally working(GET request for search hotel)
    @PostMapping("/search")
    public ResponseEntity<Page<HotelMinPriceDto>> searchHotelsPost(
            @RequestBody HotelSearchRequest hotelSearchRequest) {
        Page<HotelMinPriceDto> page = inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{hotelId}/info")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable Long hotelId){
        return ResponseEntity.ok(hotelService.getHotelInfoById(hotelId));
    }
}
