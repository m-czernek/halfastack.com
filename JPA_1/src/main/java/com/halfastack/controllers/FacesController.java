package com.halfastack.controllers;

import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.halfastack.db.BookDB;
import com.halfastack.entities.Book;

@Named
@RequestScoped
public class FacesController  {
	
	private String firstName;
	private String surname;
	private String result;
	@NotNull(message="Please choose a method")
	private String method;
	private List<Book> books;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private String title;

	public FacesController() {
		result = "";
	}
	

	@Inject
	BookDB controller;
	
	@Inject
	Conversation conversation;
	
	public void find() {
		books = controller.find(firstName, surname, method);
		log.info("Setting books: {}", books);
		result = books.size() > 0 ? "" : "Your book/author was not found. Try a different one.";
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


	public String getResult() {
		return result;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<Book> getBooks() {
		return books;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}
}
