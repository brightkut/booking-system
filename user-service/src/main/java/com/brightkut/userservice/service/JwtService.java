package com.brightkut.userservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.brightkut.commonlib.lib.exception.UnAuthorizeException;
import com.brightkut.userservice.entity.UserAuth;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserAuth userDetails, String refreshToken) {
        return generateToken(new HashMap<>(), refreshToken, userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            String refreshToken,
            UserAuth userDetails) {
        return buildToken(extraClaims, userDetails, refreshToken, jwtExpiration);
    }

    public String generateRefreshToken(
            UserAuth userDetails) {
        return buildToken(new HashMap<>(), userDetails, null, refreshExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserAuth userDetails,
            String refreshToken,
            long expiration) {

        // setup payload
        var userProfile = userDetails.getUserProfile();

        extraClaims.put("firstName", userProfile.getFirstName());
        extraClaims.put("email", userDetails.getEmail());
        extraClaims.put("lastName", userProfile.getLastName());
        extraClaims.put("refreshToken", refreshToken);

        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserAuth userDetails) {
        final String emai = extractEmail(token);
        return (emai.equals(userDetails.getEmail())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new UnAuthorizeException("Error occur when verify jwt token");
        }
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
