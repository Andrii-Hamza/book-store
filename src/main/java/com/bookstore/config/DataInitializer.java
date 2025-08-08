package com.bookstore.config;

import com.bookstore.model.Users;
import com.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for DataInitializer.
     *
     * @param userRepository  repository for managing user data
     * @param passwordEncoder password encoder for securing passwords
     */

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Runs the data initialization logic.
     *
     * @param args command-line arguments
     */
    @Override
    @Transactional
    public void run(String... args) {

        // If users already exist, do nothing
        if (userRepository.count() > 0) {
            return;
        }

        // Create a user with the admin role
        Users admin = new Users();
        admin.setUserPassword(passwordEncoder.encode("admin"));
        admin.setUserName("admin");
        admin.setUserRole("ROLE_ADMIN");

        // Create a user with the user role
        Users user = new Users();
        user.setUserPassword(passwordEncoder.encode("user"));
        user.setUserName("user");
        user.setUserRole("ROLE_USER");

        // Save users in the database
        userRepository.save(admin);
        userRepository.save(user);
    }
}
