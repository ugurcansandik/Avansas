package com.ugurcansandik.Avansas.services.impl;

import com.ugurcansandik.Avansas.entity.UserEntity;
import com.ugurcansandik.Avansas.repository.UserRepository;
import com.ugurcansandik.Avansas.services.UserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultUserDetailService implements UserDetailService {
    private final UserRepository userRepository;
    @Override
    public UserDetailsService getUserDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
            }
        };
    }

    @Override
    public Optional<UserEntity> findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }
}
