package com.example.mdbspringboot.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.mdbspringboot.model.Person;

public interface PersonRepository extends MongoRepository<Person, String> {
	
	public List<Person> findByFirstName(String firstName);
	public List<Person> findByLastName(String lastName);

}
