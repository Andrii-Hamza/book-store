package com.bookstore.repository;


import com.bookstore.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser, Long> {

    Optional<MyUser> findByUserName(String userName);
}
