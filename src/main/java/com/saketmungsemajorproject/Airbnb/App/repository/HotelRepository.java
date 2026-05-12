package com.saketmungsemajorproject.Airbnb.App.repository;

import com.saketmungsemajorproject.Airbnb.App.entity.Hotel;
import com.saketmungsemajorproject.Airbnb.App.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
}
