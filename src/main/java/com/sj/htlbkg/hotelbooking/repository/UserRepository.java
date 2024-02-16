package com.sj.htlbkg.hotelbooking.repository;

import com.sj.htlbkg.hotelbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
//    int create(User user);
//
//    User findByUsername(String username);
//
//    User login(String email, String password);

    Optional<User> findByEmail(String email);
}
