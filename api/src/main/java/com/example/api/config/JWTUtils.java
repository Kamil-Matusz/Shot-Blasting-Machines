package com.example.api.config;

import com.example.api.dto.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Utility class for working with JSON Web Tokens (JWT).
 */
@Component
public class JWTUtils {

    private final String secretKey;

    /**
     * Constructs a JWTUtils with the specified secret key.
     *
     * @param secretKey the secret key used for signing the JWTs.
     */
    public JWTUtils(@Value("${jwt.secretKey}") String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * Extracts the username from the JWT.
     *
     * @param token the JWT token.
     * @return the username contained in the token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date from the JWT.
     *
     * @param token the JWT token.
     * @return the expiration date of the token.
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Checks if the token contains a specific claim.
     *
     * @param token the JWT token.
     * @param claimName the name of the claim to check.
     * @return true if the claim is present, false otherwise.
     */
    public boolean hasClaim(String token, String claimName) {
        final Claims claims = extractAllClaims(token);
        return claims.get(claimName) != null;
    }

    /**
     * Extracts a claim from the JWT using a resolver function.
     *
     * @param token the JWT token.
     * @param claimsResolver a function to extract the desired claim from the token.
     * @param <T> the type of the claim.
     * @return the extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the JWT.
     *
     * @param token the JWT token.
     * @return the claims contained in the token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    /**
     * Checks if the token is expired.
     *
     * @param token the JWT token.
     * @return true if the token is expired, false otherwise.
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Generates a JWT token for the specified user details.
     *
     * @param userDetails the user details.
     * @return the generated JWT token.
     */
    public String generateToken(CustomUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        var user = userDetails.getUser();
        claims.put("id", user.getId());
        claims.put("name", user.getName());
        claims.put("email", user.getEmail());
        return createToken(claims, userDetails);
    }

    /**
     * Creates a JWT token with the specified claims and user details.
     *
     * @param claims the claims to include in the token.
     * @param userDetails the user details.
     * @return the generated JWT token.
     */
    private String createToken(Map<String, Object> claims, UserDetails userDetails) {

        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    /**
     * Validates the JWT token.
     *
     * @param token the JWT token.
     * @param userDetails the user details.
     * @return true if the token is valid, false otherwise.
     */
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
