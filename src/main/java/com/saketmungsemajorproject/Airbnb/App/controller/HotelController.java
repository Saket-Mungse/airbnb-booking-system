package com.saketmungsemajorproject.Airbnb.App.controller;

import com.saketmungsemajorproject.Airbnb.App.dto.BookingDto;
import com.saketmungsemajorproject.Airbnb.App.dto.HotelDto;
import com.saketmungsemajorproject.Airbnb.App.dto.HotelReportDto;
import com.saketmungsemajorproject.Airbnb.App.service.BookingService;
import com.saketmungsemajorproject.Airbnb.App.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/hotels")
//This controller we are using for hotel manager & admins
public class HotelController {

    private final HotelService hotelService;
    private final BookingService bookingService;

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

    @GetMapping
    public ResponseEntity<List<HotelDto>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("/{hotelId}/bookings")
    public ResponseEntity<List<BookingDto>> getAllBookingsByHotelId(@PathVariable Long hotelId) {
        return ResponseEntity.ok(bookingService.getAllBookingsByHotelId(hotelId));
    }

    @GetMapping("/{hotelId}/reports")
    public ResponseEntity<HotelReportDto> getHotelReport(@PathVariable Long hotelId,
                                                         @RequestParam(required = false) LocalDate startDate,
                                                         @RequestParam(required = false) LocalDate endDate) {

        if (startDate == null) startDate = LocalDate.now().minusMonths(1);
        if (endDate == null) endDate = LocalDate.now();

        return ResponseEntity.ok(bookingService.getHotelReport(hotelId, startDate, endDate));
    }



}
