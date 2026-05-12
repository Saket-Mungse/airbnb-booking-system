package com.saketmungsemajorproject.Airbnb.App.repository;

import com.saketmungsemajorproject.Airbnb.App.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
