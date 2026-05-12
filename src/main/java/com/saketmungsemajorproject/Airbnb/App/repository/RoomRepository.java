package com.saketmungsemajorproject.Airbnb.App.repository;

import com.saketmungsemajorproject.Airbnb.App.entity.Inventory;
import com.saketmungsemajorproject.Airbnb.App.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Long> {
    List<Room> findAllRoomsByHotelId(Long hotelId);
}
