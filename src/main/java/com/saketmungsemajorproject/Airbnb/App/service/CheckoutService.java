package com.saketmungsemajorproject.Airbnb.App.service;

import com.saketmungsemajorproject.Airbnb.App.entity.Booking;

public interface CheckoutService {

    String getCheckoutSession(Booking booking, String successUrl, String failureUrl);

}
