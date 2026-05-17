package com.saketmungsemajorproject.Airbnb.App.service;

import com.saketmungsemajorproject.Airbnb.App.dto.ProfileUpdateRequestDto;
import com.saketmungsemajorproject.Airbnb.App.dto.UserDto;
import com.saketmungsemajorproject.Airbnb.App.entity.User;

public interface UserService {
    User getUserById(Long id);

    void updateProfile(ProfileUpdateRequestDto profileUpdateRequestDto);

    UserDto getMyProfile();
}
