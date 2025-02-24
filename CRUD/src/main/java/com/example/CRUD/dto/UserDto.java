package com.example.CRUD.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String name;
    private String phoneNumber;
    private String uniqueCode;
    private String emailId;
    private String status;
    private Integer createdBy;
    private Integer updatedBy;



}
