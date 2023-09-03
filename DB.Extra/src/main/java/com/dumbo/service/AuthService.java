package com.dumbo.service;

import com.dumbo.exceptions.AppError;
import com.dumbo.dtos.JwtRequest;
import com.dumbo.dtos.JwtResponse;
import com.dumbo.dtos.RegistrationUserDto;
import com.dumbo.dtos.UserDto;
import com.dumbo.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.dumbo.utils.JwtTokenUtils;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException exception) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Invalid username or password"), HttpStatus.UNAUTHORIZED);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }


    public ResponseEntity<?> registerNewUser(@RequestBody RegistrationUserDto registrationUserDto){
        if (usernameExists(registrationUserDto.getUsername())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), String.format("'%s' already exists", registrationUserDto.getUsername())), HttpStatus.BAD_REQUEST);
        }
        User user = userDetailsService.CreateNewUser(registrationUserDto);
        return ResponseEntity.ok(new UserDto(user.getId(), user.getUsername()));
    }

    private boolean usernameExists(String username) {
        return userDetailsService.findByUsername(username).isPresent();
    }
}
