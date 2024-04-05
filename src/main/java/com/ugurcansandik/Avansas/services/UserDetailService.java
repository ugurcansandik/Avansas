package com.ugurcansandik.Avansas.services;

import com.ugurcansandik.Avansas.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserDetailService {
    UserDetailsService getUserDetailsService();
    Optional<UserEntity> findByUsername(String userName);
}
