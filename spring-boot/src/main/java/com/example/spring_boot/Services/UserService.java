package com.example.spring_boot.Services;

import com.example.spring_boot.Entity.User;
import com.example.spring_boot.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User GetUserById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User not found"));
    }
}
