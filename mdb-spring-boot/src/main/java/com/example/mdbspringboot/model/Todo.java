package com.example.mdbspringboot.model;

//import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Todo {
	
	@Id
	private String id;
	
	private String name;
	
	private boolean active;
	
	public Todo() {}
	
	public Todo(String name, boolean active) {
		this.setName(name);
		this.setActive(active);
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
    public String toString() {
        return String.format(
                "Todo[id=%s, name='%s', active='%s']",
                id, name, active);
    }
}
