package com.saketmungsemajorproject.Airbnb.App.strategy;

import com.saketmungsemajorproject.Airbnb.App.entity.Inventory;

import java.math.BigDecimal;

public interface PricingStrategy{
    BigDecimal calculatePrice(Inventory inventory);
}
