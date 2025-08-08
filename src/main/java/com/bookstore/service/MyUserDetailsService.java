package com.bookstore.service;

import com.bookstore.model.UserPrincipal;
import com.bookstore.model.Users;
import com.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Custom implementation of Spring Security's {@link UserDetailsService}.
 * This service is responsible for loading user-specific data during the authentication process.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Constructs a new MyUserDetailsService with a UserRepository.
     *
     * @param userRepository the repository for user data access.
     */
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Locates the user based on the username.
     * The username corresponds to the user's login ID.
     *
     * @param userName the username identifying the user whose data is required.
     * @return a fully populated user record (a {@link UserDetails} object).
     * @throws UsernameNotFoundException if the user could not be found or the user has no GrantedAuthority.
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        // Find the user by their username in the database
        Users user = userRepository.findByUserName(userName);

        // Create a list of authorities based on the user's role.
        // Spring Security expects roles to be in the format "ROLE_..."
        // In this implementation, the prefix is handled by a separate configuration.
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(user.getUserRole().toUpperCase())
        );

        // If no user is found, throw an exception
        if(user == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }

        // Return a UserPrincipal object, which wraps the Users entity
        // and implements the UserDetails interface.
        return new UserPrincipal(user, authorities);
    }
}



