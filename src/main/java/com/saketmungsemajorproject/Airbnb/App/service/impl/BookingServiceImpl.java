package com.saketmungsemajorproject.Airbnb.App.service.impl;

import com.saketmungsemajorproject.Airbnb.App.dto.BookingDto;
import com.saketmungsemajorproject.Airbnb.App.dto.BookingRequest;
import com.saketmungsemajorproject.Airbnb.App.dto.GuestDto;
import com.saketmungsemajorproject.Airbnb.App.entity.*;
import com.saketmungsemajorproject.Airbnb.App.entity.enums.BookingStatus;
import com.saketmungsemajorproject.Airbnb.App.exception.ResourceNotFoundException;
import com.saketmungsemajorproject.Airbnb.App.exception.UnAuthorisedException;
import com.saketmungsemajorproject.Airbnb.App.repository.*;
import com.saketmungsemajorproject.Airbnb.App.service.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    private final GuestRepository guestRepository;

    @Override
    @Transactional
    public BookingDto initialiseBooking(BookingRequest bookingRequest) {
        Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId())
                .orElseThrow(()->new ResourceNotFoundException("Hotel not found"));
        Room room = roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(()->new ResourceNotFoundException("Room not found"));

        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(
                room.getId(),bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate(),bookingRequest.getRoomsCount()
        );

        long daysCount = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate())+1;

        if(inventoryList.size()!=daysCount){
            throw new IllegalStateException("Rooms Not Available Anymore");
        }

        //Reserve the room/ update the reserved count of inventories

        for(Inventory inventory : inventoryList){
            inventory.setReservedCount(inventory.getReservedCount()+ bookingRequest.getRoomsCount());
        }

        inventoryRepository.saveAll(inventoryList);


        //Create the booking



        //TODO : Calculate Dynamic amount

        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .hotel(hotel)
                .room(room)
                .user(getCurrentUser())
                .roomsCount(bookingRequest.getRoomsCount())
                .amount(BigDecimal.TEN)
                .build();

        booking = bookingRepository.save(booking);
        return modelMapper.map(booking,BookingDto.class);
    }

    @Override
    public BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(()->new ResourceNotFoundException("Booking not found with id "+bookingId));
        User user = getCurrentUser();
        if(!user.equals(booking.getUser())){
            throw new UnAuthorisedException("Booking does not belong to this user with id: "+user.getId());
        }

        if(hasBookingExpired(booking)){
            throw new IllegalStateException("Booking has expired");
        }

        if(booking.getBookingStatus() != BookingStatus.RESERVED){
            throw new IllegalStateException("Booking is not in the reserved state , so cannot add the guests");
        }

        for(GuestDto guestDto : guestDtoList){
            Guest guest = modelMapper.map(guestDto,Guest.class);
            guest.setUser(getCurrentUser());
            guest = guestRepository.save(guest);
            booking.getGuests().add(guest);
        }

        booking.setBookingStatus(BookingStatus.GUEST_ADDED);
        booking = bookingRepository.save(booking);
        return modelMapper.map(booking,BookingDto.class);

    }

    public boolean hasBookingExpired(Booking booking){
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}