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

import com.example.mdbspringboot.model.Todo;
import com.example.mdbspringboot.repo.TodoRepository;

@RestController
@RequestMapping(value = "/todos")
@CrossOrigin(origins = { "http://localhost:3000" })
public class TodoController {

	@Autowired
	private TodoRepository repository;
	
	@GetMapping("")
	public List<Todo> selectAll() {
		return repository.findAll();
	}
	
	@PostMapping("/insert")
	public void insert(@RequestBody Todo todo) {
		repository.save(todo);
	}
	
	@PatchMapping("/{id}")
	public void update(@PathVariable String id, @RequestBody Todo todo) {
		Todo oldTodo = repository.findById(id).get();
		
		if (todo != null) {
			if (todo.getName() != null) {
				oldTodo.setName(todo.getName());
			}
			oldTodo.setActive(todo.isActive());
		}
		
		repository.save(oldTodo);
	}
	
	@DeleteMapping("/delete/{id}")
	public void Delete(@PathVariable String id) {
		Todo deleteTodo = repository.findById(id).get();
		
		repository.delete(deleteTodo);
	}
	
	@DeleteMapping("/delete")
	public void DeleteAll() {
		repository.deleteAll();
	}
}
