package com.ugurcansandik.Avansas.facades.impl;

import com.ugurcansandik.Avansas.dto.request.LoginRequestDto;
import com.ugurcansandik.Avansas.dto.request.RegisterRequestDto;
import com.ugurcansandik.Avansas.dto.request.UpdateRequestDto;
import com.ugurcansandik.Avansas.dto.response.UserResponseDto;
import com.ugurcansandik.Avansas.entity.RoleEnum;
import com.ugurcansandik.Avansas.entity.UserEntity;
import com.ugurcansandik.Avansas.facades.UserFacade;
import com.ugurcansandik.Avansas.services.UserService;
import com.ugurcansandik.Avansas.validate.ValidateLogin;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultUserFacade implements UserFacade {

    private final UserService userService;
    private final ValidateLogin validateLogin;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean signUp(RegisterRequestDto registerRequestDto) {
        try {
            if (userService.getUserByUsername(registerRequestDto.getUsername()).isPresent()) {
                return false;
            }
            userService.signUp(mapToUserEntity(registerRequestDto));
        } catch (EntityNotFoundException ex) {
            log.error("User not found for given information", ex);
        }
        return true;
    }

    private UserEntity mapToUserEntity(RegisterRequestDto registerRequestDto) {
        return UserEntity.builder().username(registerRequestDto.getUsername()).
                firstName(registerRequestDto.getFirstName()).
                lastName(registerRequestDto.getLastName()).
                email(registerRequestDto.getEmail()).
                phone(registerRequestDto.getPhone()).
                birthday(LocalDate.parse(registerRequestDto.getBirthday())).
                password(passwordEncoder.encode(registerRequestDto.getPassword())).
                roleEnum(RoleEnum.USER).
                build();
    }

    private static UserResponseDto mapToUserDto(UserEntity user) {
        return UserResponseDto.builder().username(user.getUsername()).
                firstName(user.getFirstName()).
                lastName(user.getLastName()).
                email(user.getEmail()).
                phone(user.getPhone()).
                birthday(user.getBirthday().toString()).
                build();
    }

    @Override
    public String signIn(LoginRequestDto user, HttpServletResponse response) {
        String validate = validateLogin.validate(user);
        return StringUtils.isEmpty(validate) ? userService.signIn(user, response) : validate;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<UserResponseDto> userList = new ArrayList<>();
        for (UserEntity user : userService.getAllUsers()) {
            userList.add(mapToUserDto(user));
        }
        return userList;
    }

    @Override
    public void deleteUser(String username) {
        Optional<UserEntity> user = userService.getUserByUsername(username);
        userService.deleteUser(user.get());
    }

    @Override
    public UserResponseDto getUserByUsername(String username) {
        UserEntity userEntity = userService.getUserByUsername(username).get();
        return mapToUserDto(userEntity);
    }

    @Override
    public void updateUser(UpdateRequestDto user) {
        UserEntity userEntity = userService.getUserByUsername(user.getUsername()).get();
        populateUser(user, userEntity);
        userService.updateUser(userEntity);
    }

    private void populateUser(UpdateRequestDto source, UserEntity target) {
        target.setEmail(source.getEmail());
        target.setPhone(source.getPhone());
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setBirthday(LocalDate.parse(source.getBirthday()));
    }

}
