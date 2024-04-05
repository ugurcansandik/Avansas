package com.ugurcansandik.Avansas.repository;

import com.ugurcansandik.Avansas.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByUsername(String username);
    @Query(value = "SELECT * FROM user WHERE user.role_enum != 'ADMIN'", nativeQuery = true)
    List<UserEntity> getAllUsers();

}
