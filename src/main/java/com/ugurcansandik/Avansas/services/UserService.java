package com.ugurcansandik.Avansas.services;

import com.ugurcansandik.Avansas.dto.request.LoginRequestDto;
import com.ugurcansandik.Avansas.entity.UserEntity;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService {
    String signIn(LoginRequestDto user, HttpServletResponse response);
    void signUp(UserEntity user);
    Optional<UserEntity> getUserByUsername(String username);
    List<UserEntity> getAllUsers();
    void deleteUser(UserEntity user);
    void updateUser(UserEntity user);
}
