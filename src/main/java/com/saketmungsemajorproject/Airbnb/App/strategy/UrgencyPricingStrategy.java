package com.saketmungsemajorproject.Airbnb.App.strategy;

import com.saketmungsemajorproject.Airbnb.App.entity.Inventory;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
public class UrgencyPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrapped;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrapped.calculatePrice(inventory);
        //wrapped = OccupancyPricingStrategy;

        if(inventory.getDate().isAfter(LocalDate.now()) && LocalDate.now().plusDays(7).isAfter(inventory.getDate())){
            price = price.multiply(BigDecimal.valueOf(1.15));
        }

        return price;
    }
}