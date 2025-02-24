package com.example.CRUD.controller;

import com.example.CRUD.dto.UserDto;
import com.example.CRUD.entity.User;
import com.example.CRUD.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Get User by ID
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);

    }
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return "User with ID " + id + " has been deleted successfully.";
    }

    @PostMapping("/create")
    public User createUsers(@RequestBody UserDto userDTO) {

        return userService.CreateUser(userDTO);
    }

    @PostMapping("/{id}")
    public User updateUser(@PathVariable Integer id, @RequestBody UserDto userDTO) {
        User updatedUser = userService.updateUser(id, userDTO);
        return updatedUser;
    }
}