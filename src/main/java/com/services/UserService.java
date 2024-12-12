package com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entities.Role;
import com.entities.User;
import com.exceptions.BadRequestException;
import com.exceptions.ResourceNotFoundException;
import com.repos.RoleRepo;
import com.repos.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepository;

    @Autowired
    private RoleRepo roleRepository;
    @Transactional
    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new BadRequestException("Username already exists");
        }
        
     // Check if the Role exists
        Role role = roleRepository.findByName(user.getRole().getName()).orElse(null);
        if (role == null) {
            // If role does not exist, create and save it
            role = new Role(user.getRole().getName());
            roleRepository.save(role);
        }

        // Set the role to the user
        user.setRole(role);
        
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }
    
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @Transactional
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));
    }
    
    @Transactional
    public User updateUser(Long id, User userDetails) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found"));
     // Check if the Role exists
        Role role = roleRepository.findByName(userDetails.getRole().getName())
                .orElseThrow(() -> new BadRequestException("Role not found"));
        
        // Update user fields
        existingUser.setUsername(userDetails.getUsername());
        existingUser.setPassword(userDetails.getPassword());
        existingUser.setFirstName(userDetails.getFirstName());
        existingUser.setLastName(userDetails.getLastName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setRole(role);

        return userRepository.save(existingUser);
    }
    
    @Transactional
    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("User not found"));
        
        userRepository.delete(existingUser);
    }
}
