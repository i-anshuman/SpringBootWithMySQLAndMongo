package com.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.SigninDTO;
import com.app.dto.TokenDTO;
import com.app.pojos.User;
import com.app.service.UserDetailsService;
import com.app.utils.JWTUtils;

@RestController
@RequestMapping("/account")
public class AccountController {
  
  @Autowired
  private UserDetailsService userDetailsService;
  
  @Autowired
  private JWTUtils jwt;
    
  @Autowired
  private AuthenticationManager authManager;
  
  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody User user) {
    System.out.println("Signup : " + user);
    return ResponseEntity.ok(userDetailsService.save(user));
  }
  
  @PostMapping("/signin")
  public ResponseEntity<?> signin(@RequestBody SigninDTO user) throws Exception {
    System.out.println("Sign in : " + user);
    try {
      /*Authentication principal = */
      authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
      //System.out.println("Principal : " + principal);
    }
    catch (DisabledException ex) {
      throw new Exception("Account disabled.", ex);
    }
    catch (LockedException ex) {
      throw new Exception("Account locked.", ex);
    }
    catch (BadCredentialsException ex) {
      throw new Exception("Bad credentials.", ex);
    }
    
    UserDetails persistedUser = userDetailsService.loadUserByUsername(user.getUsername());
    //System.out.println("Persisted User : " + persistedUser);
    return ResponseEntity.ok(new TokenDTO(jwt.generateToken(persistedUser)));
  }
}
