package com.halfastack.controllers;

import java.util.List;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
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
	
	@NotNull(message="Please enter an Author or a book title")
	private String name;
	private String result;
	@NotNull(message="Please choose a method")
	private String method;
	private List<Book> books;
	private Logger log = LoggerFactory.getLogger(this.getClass());

	public FacesController() {
		result = "";
	}
	

	@Inject
	BookDB controller;
	
	@Inject
	Conversation conversation;
	
	public void find() {
		books = controller.find(name, method);
		log.info("Setting books: {}", books);
		result = books.size() > 0 ? "" : "Your book/author was not found. Try a different one.";
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
