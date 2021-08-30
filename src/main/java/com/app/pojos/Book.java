package com.app.pojos;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
public class Book {
  @Id
  private String id;
  
  private String title;
  private String author;
  private double price;
  
  public Book() {}

  public Book(String id, String title, String author, double price) {
    super();
    this.id = id;
    this.title = title;
    this.author = author;
    this.price = price;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  @Override
  public String toString() {
    return "Book [id=" + id + ", title=" + title + ", author=" + author + ", price=" + price + "]";
  }
}
