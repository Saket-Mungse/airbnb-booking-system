package com.saketmungsemajorproject.Airbnb.App.dto;

import com.saketmungsemajorproject.Airbnb.App.entity.Hotel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoomDto {
    private Long id;
    //One Thing to notice here that your data object(RoomDto) is returning another data object(Hotel)
    //So you need to clarify one thing that it is(Hotel) returning its data EAGER OR LAZY??
    //So When you get RoomDto you will get Hotel Object Data automatically because by default it is EAGER
    //But you can change it to LAZY(So we can fetch the data when we need it) in RoomEntity
    //So we do not need the hotel info when we are getting room info
    //because we are on the page of the hotel and inside that hotel page we have our room info so we do not need it
    //private Hotel hotel;
    private String type;
    private BigDecimal basePrice;
    private String[] photos;
    private String[] amenities;
    private Integer totalCount;
    private Integer capacity;
}
