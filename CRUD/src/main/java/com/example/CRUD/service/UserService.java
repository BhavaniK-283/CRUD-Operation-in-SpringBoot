package com.example.CRUD.service;

import com.example.CRUD.dto.ApiResponsedto;
import com.example.CRUD.dto.UpdateUserDto;
import com.example.CRUD.dto.UserDto;
import com.example.CRUD.dto.UserListDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {

    UserDto getUserById(Integer id);

    UserListDto getAllUsers(String status, String sortOrder, int offset, int limit);

    ApiResponsedto deleteUserById(Integer id);

    ApiResponsedto createUser(UserDto userDTO);

    ApiResponsedto updateUser(Integer id, UpdateUserDto updateUserDTO);

    List<UserDto> getUsersByName(String name);
}
