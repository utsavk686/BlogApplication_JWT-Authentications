package com.wipro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.entity.UserEntity;
import com.wipro.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  

    public UserEntity registerUser(UserEntity user) {
      
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken.");
        }

  
        user.setPassword(passwordEncoder.encode(user.getPassword()));

       
        return userRepository.save(user);
    }
}