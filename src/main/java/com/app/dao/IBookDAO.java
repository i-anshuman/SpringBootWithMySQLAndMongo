package com.app.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.pojos.Book;

@Repository
public interface IBookDAO extends MongoRepository<Book, String> {
}
