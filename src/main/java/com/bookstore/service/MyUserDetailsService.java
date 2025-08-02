package com.bookstore.service;


import com.bookstore.config.MyUserDetails;
import com.bookstore.entity.MyUser;
import com.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<MyUser> user = userRepository.findByUserName(userName);
        return user.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(userName + " not found"));
    }
}
