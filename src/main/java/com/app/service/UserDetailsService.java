package com.app.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dao.IUserDAO;
import com.app.pojos.User;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

  @Autowired
  private IUserDAO userDAO;
  
  @Autowired
  private PasswordEncoder passwordEncoder;
  
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userDAO.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User \"" + username + "\" not found.");
    }
    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
  }

  public User save(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userDAO.save(user);
  }
}
