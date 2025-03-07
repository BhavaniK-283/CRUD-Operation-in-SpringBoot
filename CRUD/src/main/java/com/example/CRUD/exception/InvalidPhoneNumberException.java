package com.example.CRUD.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPhoneNumberException extends CustomException {
    public InvalidPhoneNumberException(String message) {
        super(message,HttpStatus.BAD_REQUEST);
    }
}
