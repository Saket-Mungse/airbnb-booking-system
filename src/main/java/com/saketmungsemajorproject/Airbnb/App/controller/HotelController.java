package com.saketmungsemajorproject.Airbnb.App.controller;

import com.saketmungsemajorproject.Airbnb.App.dto.HotelDto;
import com.saketmungsemajorproject.Airbnb.App.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/hotels")
//This controller we are using for hotel manager & admins
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelDto> createNewHotel(@RequestBody HotelDto hotelDto){
        return new ResponseEntity<>(hotelService.createNewHotel(hotelDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long id){
        return ResponseEntity.ok(hotelService.getHotelById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelDto> updateExistingHotel(@PathVariable Long id, @RequestBody HotelDto hotelDto){
        return ResponseEntity.ok(hotelService.updateExistingHotel(id,hotelDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> softDeleteTheHotel(@PathVariable Long id){
        hotelService.softDeleteTheHotel(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateTheHotelById(@PathVariable Long id){
        hotelService.activateTheHotelById(id);
        return ResponseEntity.noContent().build();
    }

}
