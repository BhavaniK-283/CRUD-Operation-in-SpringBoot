package com.example.CRUD.mapper;

import com.example.CRUD.dto.UpdateUserDto;
import com.example.CRUD.dto.UserDto;
import com.example.CRUD.entity.User;
import com.example.CRUD.enumurators.EnumUserStatus;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        UserDto userDTO = new UserDto();
        userDTO.setName(user.getName());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setUniqueCode(user.getUniqueCode());
        userDTO.setEmailId(user.getEmailId());
        userDTO.setStatus(user.getStatus().toString());
        userDTO.setCreatedBy(user.getCreatedBy());
        userDTO.setUpdatedBy(user.getUpdatedBy());
        return userDTO;
    }

    public UpdateUserDto toUpdateDto(User user) {
        UpdateUserDto userDTO = new UpdateUserDto();
        userDTO.setName(user.getName());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setUniqueCode(user.getUniqueCode());
        userDTO.setEmailId(user.getEmailId());
        userDTO.setStatus(user.getStatus().toString());
        userDTO.setUpdatedBy(user.getUpdatedBy());
        return userDTO;
    }

    public User toEntity(UserDto userDTO) {

        User user = new User();
        user.setName(userDTO.getName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setUniqueCode(userDTO.getUniqueCode());
        user.setEmailId(userDTO.getEmailId());
        user.setStatus(EnumUserStatus.ACTIVE);
        user.setCreatedBy(userDTO.getCreatedBy());
        user.setUpdatedBy(userDTO.getUpdatedBy());

        return user;
    }

    public User updateToEntity(UpdateUserDto updateUserDto, User existingUser) {


        existingUser.setName(updateUserDto.getName());
        existingUser.setPhoneNumber(updateUserDto.getPhoneNumber());
        existingUser.setEmailId(updateUserDto.getEmailId());
        existingUser.setUpdatedBy(updateUserDto.getUpdatedBy());
        existingUser.setStatus(updateUserDto.getStatus() != null && updateUserDto.getStatus().trim().equalsIgnoreCase("active")
                ? EnumUserStatus.ACTIVE
                : EnumUserStatus.INACTIVE);


        return existingUser;

    }


}