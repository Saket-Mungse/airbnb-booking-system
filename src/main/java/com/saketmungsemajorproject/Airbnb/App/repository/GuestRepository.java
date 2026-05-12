package com.saketmungsemajorproject.Airbnb.App.repository;

import com.saketmungsemajorproject.Airbnb.App.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}