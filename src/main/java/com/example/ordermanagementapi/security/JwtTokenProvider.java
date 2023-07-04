/**
 * Created By: SalahAldin Dar Aldik
 * ID Number: 1192404
 * Date: 6/26/2023
 * Time: 12:27 PM
 * Project Name: Order Management API
 */

package com.example.ordermanagementapi.security;

import com.example.ordermanagementapi.exception.OrderManagementAPIException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {


    /*In order to get the value of the JWT secret from the
application.properties file*/
    @Value("${app.jwt-secret}")
    private String jwtSecretKey;

    /*In order to get the value of the JWT expiration from the
    application.properties file*/
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMilliseconds;
    /**
     * Generates a JWT token for the provided authentication.
     *
     * @param authentication The authentication object.
     * @return The generated JWT token.
     */
    // generate token
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationInMilliseconds);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecretKey)
                .compact();
        return token;
    }


    /**
     * Retrieves the username from the provided JWT token.
     *
     * @param token The JWT token.
     * @return The username extracted from the token.
     */
    // get username from the token received from the client
    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    /**
     * Validates the provided JWT token.
     *
     * @param token The JWT token to validate.
     * @return True if the token is valid, false otherwise.
     * @throws OrderManagementAPIException If the token is invalid or expired.
     */
    // validate JWT token
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            throw new OrderManagementAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new OrderManagementAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new OrderManagementAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new OrderManagementAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new OrderManagementAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        }
    }
}

