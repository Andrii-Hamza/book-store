package com.bookstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration class for defining authentication and authorization rules.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    /**
     * Creates a BCrypt password encoder bean.
     * This encoder is used to securely hash and verify user passwords.
     *
     * @return A PasswordEncoder implementation using the BCrypt hashing algorithm.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Returns a list of publicly accessible API endpoints.
     */
    private final String[] OPENED_RESOURCES = {
            "/api/users/register",
            "/api/users/login"
    };

    /**
     * Configures the security filter chain for the application.
     * This method disables CSRF, defines public endpoints, enforces authentication
     * for all other requests, and configures stateless session management. It also
     * adds a custom JWT filter to the chain for token-based authentication.
     *
     * @param http The HttpSecurity object to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(OPENED_RESOURCES)
                        .permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())  //for insomnia
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }


    /**
     * Creates a DAO (Data Access Object) based authentication provider.
     * This provider uses a UserDetailsService to fetch user details from a data store
     * and a PasswordEncoder to verify passwords.
     *
     * @param userDetailsService The UserDetailsService to use for loading user data.
     * @return An AuthenticationProvider configured for DAO-based authentication.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    /**
     * Exposes the AuthenticationManager as a bean.
     * This manager is the central component of the authentication process
     * and is used to authenticate a user's credentials.
     *
     * @param config The AuthenticationConfiguration object to retrieve the manager from.
     * @return The configured AuthenticationManager.
     * @throws Exception If an error occurs while getting the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
