package com.example.employeemanagement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Secret key for signing tokens — keep this secret in production!
    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.expiration}")
    private long EXPIRATION;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Generate token from username
    public String generateToken( String username){      // creates a JWT token with username, issue date, expiry date, signed with secret key
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration( new Date( System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract username from token
    public String extractUsername(String token){        // reads username from token
        return extractClaims(token).getSubject();
    }

    // Check if token is valid
    public boolean isTokenValid(String token, String username){         // checks username matches and token not expired
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }
    // Check if token is expired
    private boolean isTokenExpired(String token){       // checks if token is expired by comparing expiration date with current date
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Extract claims from token
    private Claims extractClaims(String token){             //
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
