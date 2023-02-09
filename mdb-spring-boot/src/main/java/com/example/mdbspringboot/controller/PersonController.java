package com.example.mdbspringboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mdbspringboot.model.Person;
import com.example.mdbspringboot.repo.PersonRepository;

@RestController
@RequestMapping(value = "/person")
@CrossOrigin(origins = { "http://localhost:3000" })
public class PersonController {

	@Autowired
	private PersonRepository repository;
	
	@GetMapping("")
	public List<Person> selectAll() {
		return repository.findAll();
	}
	
	@GetMapping("/{id}")
	public Person getSpecificPerson(@PathVariable String id) {
		return repository.findById(id).get();
	}
	
	@GetMapping("/search/lastName/{lastName}")
	public List<Person> searchByLastName(@PathVariable String lastName) {
		return repository.findByLastName(lastName);
	}
	
	@GetMapping("/search/firstName/{firstName}")
	public List<Person> searchByFirstName(@PathVariable String firstName) {
		return repository.findByFirstName(firstName);
	}
	
	@PostMapping("/insert")
	public void insert(@RequestBody Person person) {
		repository.save(person);
	}
	
	@PatchMapping("/{id}")
	public void update(@PathVariable String id, @RequestBody Person person) {
		Person oldPerson = repository.findById(id).get();
		
		if (person != null) {
			if (person.getFirstName() != null) {
				oldPerson.setFirstName(person.getFirstName());
			}
			
			if (person.getLastName() != null) {
				oldPerson.setLastName(person.getLastName());
			}
		}
		
		repository.save(oldPerson);
	}
	
	@DeleteMapping("/delete/{id}")
	public void Delete(@PathVariable String id) {
		Person deletePerson = repository.findById(id).get();
		
		repository.delete(deletePerson);
	}
	
	@DeleteMapping("/delete")
	public void DeleteAll() {
		repository.deleteAll();
	}
}
