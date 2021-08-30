package com.app.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.mongodb.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtils {
  
  private static final long VALIDITY = 24 * 60 * 60 * 1000;
  
  @Value(value = "${jwt.secret}")
  private String secret;
  
  public Claims getAllClaims(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }
  
  public <T> T getClaims(String token, Function<Claims, T> claimResolver) {
    Claims claims = getAllClaims(token);
    return claimResolver.apply(claims);
  }

  public String getUsername(String token) {
    return getClaims(token, Claims::getSubject);
  }
  
  public Date getExpiration(String token) {
    return getClaims(token, Claims::getExpiration);
  }
  
  public boolean isExpired(String token) {
    return getExpiration(token).before(new Date());
  }
  
  public boolean isValid(String token, UserDetails user) {
    String username = getUsername(token);
    return (username.equals(user.getUsername()) && !isExpired(token));
  }
  
  public String generateToken(UserDetails user) {
    Map<String, Object> claims = new HashMap<>();
    return Jwts.builder()
            .setClaims(claims)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + VALIDITY))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
  }
}
