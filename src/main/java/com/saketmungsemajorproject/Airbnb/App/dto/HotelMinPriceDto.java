package com.saketmungsemajorproject.Airbnb.App.dto;

import com.saketmungsemajorproject.Airbnb.App.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class HotelMinPriceDto {
    private Hotel hotel;
    private BigDecimal minPrice;//Cheapest price of the hotel
}
