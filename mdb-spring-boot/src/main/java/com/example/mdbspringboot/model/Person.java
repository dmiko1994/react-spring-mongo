package com.example.mdbspringboot.model;

//import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Person {
	
	@Id
	private String id;
	
	private String firstName;
	
	private String lastName;
	
	public Person() {}
	
	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
    public String toString() {
        return String.format(
                "Person[id=%s, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }
}
