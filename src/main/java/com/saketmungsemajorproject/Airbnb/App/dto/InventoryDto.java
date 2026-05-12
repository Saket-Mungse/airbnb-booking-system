package com.saketmungsemajorproject.Airbnb.App.dto;

import com.saketmungsemajorproject.Airbnb.App.entity.Hotel;
import com.saketmungsemajorproject.Airbnb.App.entity.Room;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InventoryDto {
    private Long id;
    private Hotel hotel;
    private Room room;
    private LocalDate date;
    private Integer bookedCount;
    private Integer totalCount;
    private BigDecimal surgeFactor;
    private BigDecimal price;
    private String city;
    private Boolean closed;
}
