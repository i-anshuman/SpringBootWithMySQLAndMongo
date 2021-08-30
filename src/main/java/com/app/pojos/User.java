package com.app.pojos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

  @Column(length = 20, unique = true, nullable = false)
  private String username;
  
  @Column(length = 128)
  private String password;
  
  @Column(length = 40, nullable = false)
  private String fullname;
  
  @Column(length = 50, unique = true, nullable = false)
  private String email;
  
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate dob;
  
  @Column(name = "joining_datetime", insertable = false, updatable = false, columnDefinition = "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime joinedOn;

  public User() {}

  public User(String username, String password, String fullname, String email, LocalDate dob) {
    this.username = username;
    this.password = password;
    this.fullname = fullname;
    this.email = email;
    this.dob = dob;
  }
  
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getDob() {
    return dob;
  }

  public void setDob(LocalDate dob) {
    this.dob = dob;
  }

  public LocalDateTime getJoinedOn() {
    return joinedOn;
  }
  
  @Override
  public String toString() {
    return "User [id=" + getId() + ", username=" + username + ", password=[HIDDEN], fullname=" + fullname + ", email=" + email
        + ", dob=" + dob + ", joinedOn=" + joinedOn + "]";
  }
}
