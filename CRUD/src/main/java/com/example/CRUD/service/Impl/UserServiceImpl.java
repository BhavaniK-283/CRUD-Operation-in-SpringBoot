package com.example.CRUD.service.Impl;

import com.example.CRUD.constants.ResponseConstants;
import com.example.CRUD.dto.ApiResponsedto;
import com.example.CRUD.dto.UpdateUserDto;
import com.example.CRUD.dto.UserDto;
import com.example.CRUD.dto.UserListDto;
import com.example.CRUD.entity.User;
import com.example.CRUD.enumurators.EnumUserStatus;
import com.example.CRUD.exception.DuplicateResourceException;
import com.example.CRUD.exception.InvalidPhoneNumberException;
import com.example.CRUD.exception.UserNotFoundException;
import com.example.CRUD.mapper.UserMapper;
import com.example.CRUD.repository.UserRepository;
import com.example.CRUD.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.*;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private   UserMapper userMapper;

    @Override
    public ApiResponsedto createUser(UserDto userDTO, String createdBy) {
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
        if (userDTO.getPhoneNumber() == null || userDTO.getPhoneNumber().trim().isEmpty()) {
            throw new InvalidPhoneNumberException("Phone number cannot be empty.");
        }

        if (!userDTO.getPhoneNumber().matches("\\d+")) {
            throw new InvalidPhoneNumberException("Phone number must contain only numeric digits.");
        }

        if (userDTO.getPhoneNumber().length() < 7) {
            throw new InvalidPhoneNumberException("Phone number must have at least 7 digits.");
        }

        if (userDTO.getPhoneNumber().length() > 10) {
            throw new InvalidPhoneNumberException("Phone number must not exceed 10 digits.");
        }
        User user;
        user = userMapper.dtoToEntity(userDTO);
        user.setCreatedBy(createdBy);
        user.setUpdatedBy(createdBy);
        long uniqueNumber = userRepository.count() + 1;
        user.setUniqueCode("ATS" + String.format("%03d", uniqueNumber));
        userRepository.save(user);
        return new ApiResponsedto(HttpStatus.OK.value(), ResponseConstants.CREATED_MESSAGE);
    }

    @Override
    public UserDto getUserById(Integer id) {
        User user;
        user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
        return userMapper.userToDto(user);
    }

    @Override
    public ApiResponsedto deleteUserById(Integer id) {
        User user;
        user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
        user.setStatus(EnumUserStatus.valueOf(EnumUserStatus.INACTIVE.name()));
        userRepository.save(user);

        return new ApiResponsedto(HttpStatus.OK.value(), ResponseConstants.DELETE_MESSAGE);
    }




    @Override
    public ApiResponsedto updateUser(Integer id, UpdateUserDto updateUserDTO, String updatedBy) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if (updateUserDTO.getPhoneNumber() != null) {
            if (!updateUserDTO.getPhoneNumber().matches("\\d+")) {
                throw new InvalidPhoneNumberException("Phone number must contain only numeric digits.");
            }
            if (updateUserDTO.getPhoneNumber().length() < 7 || updateUserDTO.getPhoneNumber().length() > 10) {
                throw new InvalidPhoneNumberException("Phone number must be between 7 and 10 digits.");
            }
            if (userRepository.existsByPhoneNumberAndIdNot(updateUserDTO.getPhoneNumber(), id)) {
                throw new DuplicateResourceException("Phone number already exists: " + updateUserDTO.getPhoneNumber());
            }
            existingUser.setPhoneNumber(updateUserDTO.getPhoneNumber());
        }

        if (updateUserDTO.getEmailId() != null) {
            if (userRepository.existsByEmailIdAndIdNot(updateUserDTO.getEmailId(), id)) {
                throw new DuplicateResourceException("Email ID already exists: " + updateUserDTO.getEmailId());
            }
            existingUser.setEmailId(updateUserDTO.getEmailId());
        }

        if (updateUserDTO.getName() != null) {
            existingUser.setName(updateUserDTO.getName());
        }

        existingUser.setUpdatedBy(updatedBy);
        userRepository.save(existingUser);

        return new ApiResponsedto(HttpStatus.OK.value(), ResponseConstants.UPDATED_MESSAGE);
    }


    @Override
    public UserListDto getAllUsers(String status, String sortOrder, int offset, int limit) {
        Sort sort = sortOrder.equalsIgnoreCase("DESC") ? Sort.by(Sort.Direction.DESC, "name")
                : Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(offset, limit, sort);
        Page<User> userPage;
        try {
            EnumUserStatus userStatus = EnumUserStatus.valueOf(status.toUpperCase());
            userPage = userRepository.findByStatus(userStatus, pageable);
        } catch (IllegalArgumentException e) {
            userPage = userRepository.findAll(pageable);
        }
        UserListDto userListDTO = new UserListDto();
        userListDTO.setOffset(String.valueOf(offset));
        userListDTO.setLimit(String.valueOf(limit));
        userListDTO.setTotalSize(String.valueOf(userPage.getTotalElements()));
        userListDTO.setData(userPage.getContent().stream()
                .map(userMapper::userToDto)
                .toList());
        return userListDTO;
    }

    @Override
    public List<UserDto> getUsersBySearch(Integer id, String uniqueCode, String name, String email) {
        Specification<User> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (id != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), id));
            }
            if (uniqueCode != null && !uniqueCode.isBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("uniqueCode")), "%" + uniqueCode.toLowerCase() + "%"));
            }
            if (name != null && !name.isBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (email != null && !email.isBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("emailId")), "%" + email.toLowerCase() + "%"));
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };

        List<User> users = userRepository.findAll(spec);
        return users.stream().map(userMapper::userToDto).toList();
    }


}
