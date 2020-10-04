package edu.tec.ic6821.app.security.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    @Value("${app.authentication.jwt.secret}")
    private String jwtSecret;

    @Value("${app.authentication.jwt.expiration}")
    private long jwtExpiration;

    public String generateJwtToken(Authentication authentication) {
        UserDetails principal = (UserDetails) authentication.getPrincipal();

        Date current = new Date();

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(current)
                .setExpiration(new Date(current.getTime() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(authToken);

            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid JWT signature -> Message: {} " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token -> Message: {}" + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT token -> Message: {}" + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("Unsupported JWT token -> Message: {}" + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty -> Message: {}" + e.getMessage());
        }

        return false;
    }
}
