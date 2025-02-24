package com.example.CRUD.service;

import com.example.CRUD.dto.UserDto;
import com.example.CRUD.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

       Optional<User> getUserById(Integer id);

       List<User> getAllUsers();


       void deleteUserById(Integer id);


       User CreateUser(UserDto  userDTO);



       User updateUser(Integer id, UserDto userDTO);
}
