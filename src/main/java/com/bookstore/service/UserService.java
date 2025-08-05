package com.bookstore.service;

import com.bookstore.dto.UserMapper;
import com.bookstore.dto.UserRegistrationDTO;
import com.bookstore.model.Role;
import com.bookstore.model.User;
import com.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Service for managing user authentication and registration.
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Loads user details by username (email).
     *
     * @param username the username (email) of the user
     * @return the user details
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByEmail(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities() // это должно быть Set<Role>, где Role реализует GrantedAuthority
        );
    }

    /**
     * Registers a new user with a default "ROLE_USER" authority.
     *
     * @param userRegistrationDTO the user registration data transfer object
     * @return the saved user entity
     */
    @Transactional
    public User registerUser(UserRegistrationDTO userRegistrationDTO) {
        User newUser = userMapper.toUser(userRegistrationDTO);

        Role userRole = new Role();
        userRole.setAuthority("ROLE_USER");

        newUser.setAuthorities(Set.of(userRole));

        return userRepository.save(newUser);
    }
}
