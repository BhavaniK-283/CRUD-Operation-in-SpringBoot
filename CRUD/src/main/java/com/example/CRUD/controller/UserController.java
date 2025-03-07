package com.example.CRUD.controller;


import com.example.CRUD.dto.ApiResponsedto;
import com.example.CRUD.dto.UpdateUserDto;
import com.example.CRUD.dto.UserDto;
import com.example.CRUD.dto.UserListDto;
import com.example.CRUD.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public UserListDto getAllUsers(@RequestParam(defaultValue = "BOTH") String status,
                                   @RequestParam(defaultValue = "ASC") String sortOrder,
                                   @RequestParam(defaultValue = "0") Integer offset,
                                   @RequestParam(defaultValue = "2") Integer limit) {
        return userService.getAllUsers(status, sortOrder, offset, limit);

    }

    @DeleteMapping("/{id}")
    public ApiResponsedto deleteUser(@PathVariable Integer id) {
        return userService.deleteUserById(id);

    }

    @PostMapping("/create")
    public ApiResponsedto createUser(@RequestBody UserDto userDTO, @RequestHeader("createdBy") String createdBy) {
        return userService.createUser(userDTO, createdBy);
    }

    @PostMapping("/{id}")
    public ApiResponsedto updateUser(@PathVariable Integer id, @RequestBody UpdateUserDto updateUserDTO, @RequestHeader("updatedBy") String updatedBy) {
        return userService.updateUser(id, updateUserDTO, updatedBy);
    }

    @GetMapping("/search")
    public List<UserDto> getUsersBySearch(@RequestParam(required = false) Integer id,
                                          @RequestParam(required = false) String uniqueCode,
                                          @RequestParam(required = false) String name,
                                          @RequestParam(required = false) String email) {
        return userService.getUsersBySearch(id, uniqueCode, name,email);
    }
}