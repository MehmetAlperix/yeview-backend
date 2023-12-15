package com.kolaykira.webapp.authentication.jwt;

import com.kolaykira.webapp.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    //CONSTANTS
    final long EXPE_TIME = (long) (1000*60*60*2.5);

    //JWT_KEY is a random base64 256 bits key that is stored as a system environment value
    @Value("${JWT_KEY}")
    private String key;

    public String extractEmail(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(User user)
    {
        return generateToken(new HashMap<>(), user);
    }
    public String generateToken(Map<String, Object> extraClaims, User user)
    {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject( user.getEmail())
                .setIssuedAt( new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPE_TIME))
                .signWith(getSignKey(),SignatureAlgorithm.HS256)
                .compact();

    }
    public <T> T extractClaim(String token, Function <Claims, T> claimsResolver)
    {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }
    public boolean isTokenValid(String token, User u)
    {
        String email = extractEmail(token);
        return (email.equals(u.getEmail()) && !isTokenExpired(token) );
    }
    private Claims extractAllClaims(String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    private SecretKey getSignKey()
    {
        byte[] keyBytes = Base64.getDecoder().decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token)
    {
        return extractClaim(token, Claims::getExpiration);
    }

}


