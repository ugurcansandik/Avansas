package com.ugurcansandik.Avansas.dto.request;

import lombok.Data;

@Data
public class UpdateRequestDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String birthday;
}
