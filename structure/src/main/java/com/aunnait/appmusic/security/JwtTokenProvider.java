package com.aunnait.appmusic.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

@Component
public class JwtTokenProvider {

    private final SecretKey secretKey = io.jsonwebtoken.security.Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final long expirationTime = 86400000; // 1 day

    // Generate Token
    public String generateToken(String username, List<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
    }

    // Validate Token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.err.println("Invalid token: " + e.getMessage());
            return false;
        }
    }

    // Obtain username
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    // Extract token from request header
    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove 'Bearer' prefix
        }
        return null;
    }
}
