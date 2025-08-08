package com.bookstore.controller;

import com.bookstore.model.Users;
import com.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for user-related operations such as registration or login.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Registers a new user.
     *
     * @param user the user registration data transfer object
     * @return Users containing the created user.
     */
    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return userService.register(user);
    }

    /**
     * Processes a user login request.
     * The endpoint accepts user credentials (username and password) and verifies them.
     *
     * @param user Users object containing user credentials for login.
     * @return String containing the verification result: a JWT token and a message.
     */
    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        return userService.verify(user);
    }
}
