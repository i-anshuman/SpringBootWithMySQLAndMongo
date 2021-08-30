package com.app.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.pojos.User;

@Repository
public interface IUserDAO extends CrudRepository<User, Integer> {
  User findByUsername(String username);
}
