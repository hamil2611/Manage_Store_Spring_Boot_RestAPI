package com.example.managestore.configuration.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultJwtParser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private static final String SECRET_KEY = "123456789";
    private static final long EXPIRE_TIME = 86400000000L;
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class.getName());

    public String generateToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("ADMIN", authorities)
                .setExpiration(new Date(new Date().getTime() + 36000000))
                .signWith(SignatureAlgorithm.HS512, "123456789")
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }
        return false;
    }

    public String getUserNameFromJwtToken(String token) {

        String userName = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody().getSubject();
        return userName;
    }

    public Authentication getAuthentication(String token) {
        System.out.println("get authentication");
        DefaultJwtParser defaultJwtParser = new DefaultJwtParser();
        try{
            Claims claims = defaultJwtParser.setSigningKey("123456789").parseClaimsJws(token).getBody();
            System.out.println(claims);
            Set<GrantedAuthority> authorities = Arrays
                    .stream(claims.get("ADMIN").toString().split(","))
                    .filter(auth -> !auth.trim().isEmpty())
                    .map(auth -> new SimpleGrantedAuthority(auth))
                    .collect(Collectors.toSet());
            System.out.println(authorities);
            User principal =new User(claims.getSubject(), "",authorities);
            return new UsernamePasswordAuthenticationToken(principal,token,authorities);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
