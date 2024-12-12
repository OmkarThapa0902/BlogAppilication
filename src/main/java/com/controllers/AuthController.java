package com.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Utils.LoginRequest;
import com.entities.User;
import com.exceptions.BadRequestException;
import com.exceptions.UnauthorizedException;
import com.repos.UserRepo;
import com.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepo userRepository;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String welcome() {
    	return "Welcome to Auth Controller";
    }

    public AuthController(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

        // Check password
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            // Invalid password, return failure response
            return new ResponseEntity<>(
                Map.of(
                    "message", "Invalid username or password",
                    "Status", "Failure",
                    "StatusCode", HttpStatus.UNAUTHORIZED
                ), 
                HttpStatus.UNAUTHORIZED
            );
        }

        return new ResponseEntity<>(
                Map.of(
                    "message", "Login successful!",
                    "user", user,
                    "Status", "Success",
                    "StatusCode", HttpStatus.OK
                ), 
                HttpStatus.OK
            );
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
    	try {
            User newUser = userService.registerUser(user);
            // Send success response with the new user data
            return new ResponseEntity<>(Map.of("message","User Register Successfully.", "user",newUser ,"Status", "Success", "StatusCode", HttpStatus.CREATED), HttpStatus.CREATED);
        } catch (BadRequestException ex) {
            // Handle BadRequestException and return a proper response
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            // Handle general exception
            return new ResponseEntity<>("Failed to register user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

