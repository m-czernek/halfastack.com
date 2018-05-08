package com.halfastack.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
// Table not necessary since classname corresponds to a table AUTHOR
@XmlRootElement
public class Author {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String firstName;
	@Column(name="SECONDNAME")
	private String surname;
	
	// This is one to many:
	// One Author can have many books
	// We assume here that a book can have one and only one author
	@OneToMany(mappedBy="author")
	private List<Book> books;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<Book> getBooks() {
		if(this.books == null) {
			this.books = new ArrayList<>();
		}
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "{ "+ this.firstName + " " + this.surname + " " + this.books +"}";
	}
	
	
}
