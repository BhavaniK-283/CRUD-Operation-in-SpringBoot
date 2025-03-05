package com.example.CRUD.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException(Integer id) {
        super("User with ID " + id + " not found.", HttpStatus.NOT_FOUND);
    }
}
