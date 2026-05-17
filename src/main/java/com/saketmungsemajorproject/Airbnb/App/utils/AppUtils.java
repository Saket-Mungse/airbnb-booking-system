package com.saketmungsemajorproject.Airbnb.App.utils;

import com.saketmungsemajorproject.Airbnb.App.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppUtils {

    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
