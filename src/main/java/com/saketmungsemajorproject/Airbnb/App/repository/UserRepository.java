package com.saketmungsemajorproject.Airbnb.App.repository;

import com.saketmungsemajorproject.Airbnb.App.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
