package com.app.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
  
  @GetMapping("/greet")
  public String greet () {
    return "Spring Boot RESTFul API with MySQL and MongoDB";
  }
}
