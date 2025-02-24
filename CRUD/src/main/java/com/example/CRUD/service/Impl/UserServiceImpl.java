package com.example.CRUD.service.Impl;


import com.example.CRUD.dto.UserDto;
import com.example.CRUD.entity.User;
import com.example.CRUD.enumurators.UserStatus;
import com.example.CRUD.repository.UserRepository;
import com.example.CRUD.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.monitor.GaugeMonitor;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User CreateUser(UserDto userDTO){
        User user = new User();
        user.setName(userDTO.getName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setUniqueCode(userDTO.getUniqueCode());
        user.setEmailId(userDTO.getEmailId());
        String status = userDTO.getStatus().trim();
        if ("active".equalsIgnoreCase(status)) {
            user.setStatus(UserStatus.ACTIVE);
        } else if ("inactive".equalsIgnoreCase(status)) {
            user.setStatus(UserStatus.INACTIVE);
        } else {
            throw new IllegalArgumentException("Invalid status value: " + userDTO.getStatus() + ". Allowed values: ACTIVE, INACTIVE.");
        }
        user.setCreatedBy(userDTO.getCreatedBy());
        user.setUpdatedBy(userDTO.getUpdatedBy());
        return userRepository.save(user);
    }
    @Override
    public Optional<User> getUserById(Integer id ) {
        return userRepository.findById(id);

    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public void deleteUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        userRepository.deleteById(id);

    }

    @Override
    public User updateUser(Integer id, UserDto userDTO) {
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            // Update user fields
            existingUser.setName(userDTO.getName());
            existingUser.setPhoneNumber(userDTO.getPhoneNumber());
            existingUser.setUniqueCode(userDTO.getUniqueCode());
            existingUser.setEmailId(userDTO.getEmailId());
            String status = userDTO.getStatus().trim();
            if ("active".equalsIgnoreCase(status)) {
                existingUser.setStatus(UserStatus.ACTIVE);
            } else if ("inactive".equalsIgnoreCase(status)) {
                existingUser.setStatus(UserStatus.INACTIVE);
            } else {
                throw new IllegalArgumentException("Invalid status value: " + userDTO.getStatus() + ". Allowed values: ACTIVE, INACTIVE.");
            }

            existingUser.setUpdatedBy(userDTO.getUpdatedBy());

            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User with ID " + id + " not found.");
        }
    }







}