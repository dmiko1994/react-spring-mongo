package com.example.mdbspringboot.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.mdbspringboot.model.Todo;

public interface TodoRepository extends MongoRepository<Todo, String>{}
