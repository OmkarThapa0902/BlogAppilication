package com.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entities.User;
import com.exceptions.BadRequestException;
import com.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (BadRequestException ex) {
            return new ResponseEntity<>(
                Map.of(
                    "message", ex.getMessage(),
                    "status", "Failure",
                    "statusCode", HttpStatus.NOT_FOUND
                ),
                HttpStatus.NOT_FOUND
            );
        }
    }
    
 // Update user
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return new ResponseEntity<>(
                Map.of(
                    "message", "User updated successfully.",
                    "user", updatedUser,
                    "status", "Success",
                    "statusCode", HttpStatus.OK
                ),
                HttpStatus.OK
            );
        } catch (BadRequestException ex) {
            return new ResponseEntity<>(
                Map.of(
                    "message", ex.getMessage(),
                    "status", "Failure",
                    "statusCode", HttpStatus.NOT_FOUND
                ),
                HttpStatus.NOT_FOUND
            );
        }
    }

    // Delete user
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(
                Map.of(
                    "message", "User deleted successfully.",
                    "status", "Success",
                    "statusCode", HttpStatus.OK
                ),
                HttpStatus.OK
            );
        } catch (BadRequestException ex) {
            return new ResponseEntity<>(
                Map.of(
                    "message", ex.getMessage(),
                    "status", "Failure",
                    "statusCode", HttpStatus.NOT_FOUND
                ),
                HttpStatus.NOT_FOUND
            );
        }
    }
}

