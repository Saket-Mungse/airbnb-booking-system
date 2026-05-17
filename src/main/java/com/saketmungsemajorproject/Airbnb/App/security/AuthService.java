package com.saketmungsemajorproject.Airbnb.App.security;


import com.saketmungsemajorproject.Airbnb.App.dto.LoginDto;
import com.saketmungsemajorproject.Airbnb.App.dto.SignUpRequestDto;
import com.saketmungsemajorproject.Airbnb.App.dto.UserDto;
import com.saketmungsemajorproject.Airbnb.App.entity.User;
import com.saketmungsemajorproject.Airbnb.App.entity.enums.Role;
import com.saketmungsemajorproject.Airbnb.App.exception.ResourceNotFoundException;
import com.saketmungsemajorproject.Airbnb.App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public UserDto signUp(SignUpRequestDto signUpRequestDto) {

        User user = userRepository.findByEmail(signUpRequestDto.getEmail()).orElse(null);

        if (user != null) {
            throw new RuntimeException("User is already present with same email id");
        }

        User newUser = modelMapper.map(signUpRequestDto, User.class);
        newUser.setRoles(Set.of(Role.GUEST));//All new users are guest by default
        newUser.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));//BCrypt hash, can't be reversed
        newUser = userRepository.save(newUser);

        return modelMapper.map(newUser, UserDto.class);
    }

    public String[] login(LoginDto loginDto) {
        //Spring security verifies email+password against database
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()
        ));

        User user = (User) authentication.getPrincipal();

        String[] arr = new String[2];
        arr[0] = jwtService.generateAccessToken(user);//Expire in 10 mins
        arr[1] = jwtService.generateRefreshToken(user);// Expire in 6 months

        //Access token sent in response body(Contains user info)
        //Refresh token Stored in Http Cookie-Client can't access via JS(Contains only user id)


        return arr;
    }

    public String refreshToken(String refreshToken) {
        Long id = jwtService.getUserIdFromToken(refreshToken);

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+id));
        return jwtService.generateAccessToken(user);
    }

}
