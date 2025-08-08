package com.bookstore.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service class for handling all JWT-related operations.
 * This includes generating tokens, validating them, and extracting claims such as username and expiration date.
 */
@Service
public class JWTService {

    /**
     * The secret key used to sign and verify JWTs. It is generated once at runtime.
     * It is important to use a strong, securely stored key in a production environment.
     */
    private String SECRET_KEY = "";

    /**
     * Constructs the JWTService and generates a secure, random secret key.
     * The key is generated using HMAC-SHA256 and is encoded in Base64 for storage.
     */
    public JWTService() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            SECRET_KEY = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a new JWT token for a given username.
     * The token includes the username as the subject and has a hardcoded expiration time.
     *
     * @param username The subject of the token (e.g., the user's login name).
     * @return A signed JWT token as a String.
     */
    public String generateToken(String username){

        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30))
                .and()
                .signWith(getKey())
                .compact();

    }

    /**
     * Retrieves the SecretKey from the Base64-encoded string.
     *
     * @return The SecretKey object.
     */
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extracts the username from a JWT token.
     *
     * @param token The JWT token string.
     * @return The username (subject) from the token's claims.
     */
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * A generic method to extract a specific claim from a token.
     *
     * @param token The JWT token string.
     * @param claimResolver A function that resolves the desired claim from the Claims object.
     * @param <T> The type of the claim to be extracted.
     * @return The extracted claim.
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    /**
     * Parses the token and extracts all its claims.
     *
     * @param token The JWT token string.
     * @return A Claims object containing all claims from the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Validates a JWT token by checking if the username matches the user details
     * and if the token has not expired.
     *
     * @param token The JWT token string.
     * @param userDetails The UserDetails object of the user to validate against.
     * @return {@code true} if the token is valid, {@code false} otherwise.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Checks if a token has expired.
     *
     * @param token The JWT token string.
     * @return {@code true} if the token's expiration date is before the current date, {@code false} otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from a JWT token.
     *
     * @param token The JWT token string.
     * @return The expiration date from the token's claims.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
