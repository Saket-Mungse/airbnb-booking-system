package com.saketmungsemajorproject.Airbnb.App.service;

import com.saketmungsemajorproject.Airbnb.App.dto.BookingDto;
import com.saketmungsemajorproject.Airbnb.App.dto.BookingRequest;
import com.saketmungsemajorproject.Airbnb.App.dto.GuestDto;

import java.util.List;

public interface BookingService{
    BookingDto initialiseBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);
}
