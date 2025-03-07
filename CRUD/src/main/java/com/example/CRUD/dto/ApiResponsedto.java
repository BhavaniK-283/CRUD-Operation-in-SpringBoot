package com.example.CRUD.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Data
@NoArgsConstructor
public class ApiResponsedto {
    private int code;
    private String message;

    public ApiResponsedto(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
