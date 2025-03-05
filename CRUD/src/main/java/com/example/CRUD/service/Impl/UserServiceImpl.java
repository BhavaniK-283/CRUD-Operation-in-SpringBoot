package com.example.CRUD.service.Impl;

import com.example.CRUD.constants.ResponseConstants;
import com.example.CRUD.dto.ApiResponsedto;
import com.example.CRUD.dto.UpdateUserDto;
import com.example.CRUD.dto.UserDto;
import com.example.CRUD.dto.UserListDto;
import com.example.CRUD.entity.User;
import com.example.CRUD.enumurators.EnumUserStatus;
import com.example.CRUD.exception.DuplicateResourceException;
import com.example.CRUD.exception.UserNotFoundException;
import com.example.CRUD.mapper.UserMapper;
import com.example.CRUD.repository.UserRepository;
import com.example.CRUD.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    User user;

    @Override
    public ApiResponsedto createUser(UserDto userDTO) {
        if (userDTO.getName() == null || userDTO.getName().trim().isEmpty()) {
            return new ApiResponsedto(400, ResponseConstants.NULL_NAME_MESSAGE);
        }
        if (userDTO.getPhoneNumber() == null || userDTO.getPhoneNumber().trim().isEmpty()) {
            return new ApiResponsedto(400, ResponseConstants.NULL_PHONE_MESSAGE);
        }
        if (userDTO.getEmailId() == null || userDTO.getEmailId().trim().isEmpty()) {
            return new ApiResponsedto(400, ResponseConstants.NULL_EMAIL_MESSAGE);
        }
        if (userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
            throw new DuplicateResourceException("Phone number already exists: " + userDTO.getPhoneNumber());
        }
        if (userRepository.existsByEmailId(userDTO.getEmailId())) {
            throw new DuplicateResourceException("Email ID already exists: " + userDTO.getEmailId());
        }
        user = userMapper.toEntity(userDTO);
        long uniqueNumber = userRepository.count() + 1;
        user.setUniqueCode("ATS" + String.format("%03d", uniqueNumber));
        userRepository.save(user);
        return new ApiResponsedto(HttpStatus.OK.value(), ResponseConstants.CREATED_MESSAGE);
    }

    @Override
    public UserDto getUserById(Integer id) {
        user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toDto(user);
    }

    @Override
    public ApiResponsedto deleteUserById(Integer id) {
        user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setStatus(EnumUserStatus.INACTIVE);
        userRepository.save(user);

        return new ApiResponsedto(HttpStatus.OK.value(), ResponseConstants.DELETE_MESSAGE);
    }

    @Override
    public ApiResponsedto updateUser(Integer id, UpdateUserDto updateUserDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (userRepository.existsByPhoneNumberAndIdNot(updateUserDTO.getPhoneNumber(), id)) {
            throw new DuplicateResourceException("Phone number already exists: " + updateUserDTO.getPhoneNumber());
        }
        if (userRepository.existsByEmailIdAndIdNot(updateUserDTO.getEmailId(), id)) {
            throw new DuplicateResourceException("Email ID already exists: " + updateUserDTO.getEmailId());
        }

        existingUser = userMapper.updateToEntity(updateUserDTO, existingUser);
        userRepository.save(existingUser);
        return new ApiResponsedto(HttpStatus.OK.value(), ResponseConstants.UPDATED_MESSAGE);
    }

    @Override
    public UserListDto getAllUsers(String status, String sortOrder, int offset, int limit) {
        Sort sort = sortOrder.equalsIgnoreCase("DESC") ? Sort.by(Sort.Direction.DESC, "name")
                : Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(offset, limit, sort);
        Page<User> userPage;

        switch (status.toUpperCase()) {
            case "ACTIVE":
                userPage = userRepository.findByStatus(EnumUserStatus.ACTIVE, pageable);
                break;
            case "INACTIVE":
                userPage = userRepository.findByStatus(EnumUserStatus.INACTIVE, pageable);
                break;
            case "BOTH":
            default:
                userPage = userRepository.findAll(pageable);
                break;
        }

        UserListDto userListDTO = new UserListDto();
        userListDTO.setOffset(String.valueOf(offset));
        userListDTO.setLimit(String.valueOf(limit));
        userListDTO.setTotalSize(String.valueOf(userPage.getTotalElements()));
        userListDTO.setData(userPage.getContent().stream()
                .map(userMapper::toDto)
                .toList());
        return userListDTO;
    }

    @Override
    public List<UserDto> getUsersByName(String name) {
        List<User> users = userRepository.findByNameContainingIgnoreCase(name);
        return users.stream().map(userMapper::toDto).collect(Collectors.toList());
    }


}
