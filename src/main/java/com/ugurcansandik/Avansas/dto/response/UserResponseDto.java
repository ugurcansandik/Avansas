package com.ugurcansandik.Avansas.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String birthday;
}
