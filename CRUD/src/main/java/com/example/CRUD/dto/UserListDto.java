package com.example.CRUD.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserListDto {
    private String offset;
    private String limit;
    private String totalSize;
    private List<UserDto> data;
}

