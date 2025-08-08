package com.bookstore.service;

import com.bookstore.model.Users;
import com.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for managing user authentication and registration.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    /**
     * Service for handling JWT (JSON Web Token) operations.
     */
    @Autowired
    private JWTService jwtService;

    /**
     * The Spring Security AuthenticationManager, used to authenticate user credentials.
     */
    @Autowired
    AuthenticationManager authManager;

    /**
     * A password encoder for securely hashing and verifying user passwords.
     * The strength of the encoder is set to 12.
     */
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    /**
     * Registers a new user by encoding their password and saving them to the database.
     *
     * @param user The Users object containing the new user's details.
     * @return The newly registered Users object, as saved in the database.
     */
    public Users register(Users user) {
        user.setUserPassword(encoder.encode(user.getUserPassword()));
        return repository.save(user);
    }

    /**
     * Verifies a user's credentials and generates a JWT token upon successful authentication.
     *
     * @param user The Users object containing the username and password for verification.
     * @return A String containing the JWT token if authentication is successful, or "fail" otherwise.
     */
    public String verify(Users user) {
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getUserPassword()));

        if (authentication.isAuthenticated()) {
            return "Token: " + jwtService.generateToken(user.getUserName());
        }
        return "fail";
    }
}
