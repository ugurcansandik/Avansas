package com.ugurcansandik.Avansas.facades;

import com.ugurcansandik.Avansas.dto.request.LoginRequestDto;
import com.ugurcansandik.Avansas.dto.request.RegisterRequestDto;
import com.ugurcansandik.Avansas.dto.request.UpdateRequestDto;
import com.ugurcansandik.Avansas.dto.response.UserResponseDto;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserFacade {
    boolean signUp(RegisterRequestDto user);
    String signIn(LoginRequestDto user, HttpServletResponse response);
    List<UserResponseDto> getAllUsers();
    void deleteUser(String username);
    UserResponseDto getUserByUsername(String username);
    void updateUser(UpdateRequestDto user);
}
