package com.perevist.DeliciousFoodApp.repository;

import com.perevist.DeliciousFoodApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.authorities WHERE u.username=?1")
    Optional<User> findByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.username=?1")
    Optional<User> findByUsernameWithoutAuthorities(String username);
}
