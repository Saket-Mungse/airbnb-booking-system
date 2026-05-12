package com.saketmungsemajorproject.Airbnb.App.dto;

import com.saketmungsemajorproject.Airbnb.App.entity.User;
import com.saketmungsemajorproject.Airbnb.App.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class GuestDto {
    private Long id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;
}
