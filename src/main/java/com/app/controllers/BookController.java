package com.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dao.IBookDAO;
import com.app.pojos.Book;

@RestController
@RequestMapping("/book")
public class BookController {
  
  @Autowired
  private IBookDAO bookDAO;
  
  @PostMapping("/add")
  public ResponseEntity<?> addBook(@RequestBody Book book) {
    return ResponseEntity.ok(bookDAO.save(book));
  }
  
  @GetMapping("/list")
  public ResponseEntity<?> listBooks() {
    return ResponseEntity.ok(bookDAO.findAll());
  }
  
  @GetMapping("/{id}")
  public ResponseEntity<?> getBook(@PathVariable String id) {
    return ResponseEntity.ok(bookDAO.findById(id));
  }
}
