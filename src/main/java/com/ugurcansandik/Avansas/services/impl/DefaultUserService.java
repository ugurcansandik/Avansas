package com.ugurcansandik.Avansas.services.impl;

import com.ugurcansandik.Avansas.dto.request.LoginRequestDto;
import com.ugurcansandik.Avansas.entity.RoleEnum;
import com.ugurcansandik.Avansas.entity.UserEntity;
import com.ugurcansandik.Avansas.repository.UserRepository;
import com.ugurcansandik.Avansas.services.JwtService;
import com.ugurcansandik.Avansas.services.UserService;
import de.mkammerer.argon2.Argon2;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Override
    public String signIn(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        try {
            UserEntity user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
            if (RoleEnum.ADMIN.equals(user.getRoleEnum())){
                if (!loginRequestDto.getPassword().equals(user.getPassword())){
                    throw new IllegalArgumentException("Invalid email or password");
                }
            }else {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword()));
            }
            this.generateCookies(response,jwtService.generateToken(user));
            return null;
        }catch (Exception ex){
            return ex.getMessage();
        }
    }
    @Override
    public void signUp(UserEntity user) {
        try {
            userRepository.save(user);
        }catch (Exception e){
            log.error("An error occurred : ", e);
        }
    }

    @Override
    public void deleteUser(UserEntity user) {
        userRepository.delete(user);
    }

    @Override
    public Optional<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public void updateUser(UserEntity user) {
        userRepository.save(user);
    }
    private void generateCookies(HttpServletResponse response, String user){
        Cookie cookie = new Cookie("user", user);
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
