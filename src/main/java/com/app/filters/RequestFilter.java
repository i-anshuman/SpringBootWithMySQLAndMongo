package com.app.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.service.UserDetailsService;
import com.app.utils.JWTUtils;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class RequestFilter extends OncePerRequestFilter {
  
  @Autowired
  private JWTUtils jwt;
  
  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    System.out.println("In Request Filter");
    
    String authHeader = request.getHeader("Authorization");
    String username = null;
    String token = null;
    
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      logger.info("Bearer token is present in Authorization header.");
      token = authHeader.substring(7);
      
      try {
        username = jwt.getUsername(token);
      }
      catch (IllegalArgumentException ex) {
        System.out.println("Unable to fetch jwt token. " + ex);
      }
      catch (ExpiredJwtException ex) {
        System.out.println("JWT token is expired. " + ex);
      }
    }
    else {
      logger.warn("Bearer token is not present in Authorization header.");
    }
    
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails user = userDetailsService.loadUserByUsername(username);
      
      if (jwt.isValid(token, user)) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);
      }
    }
    
    filterChain.doFilter(request, response);
  }
}
