package com.halfastack.controllers;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.halfastack.db.BookDB;
import com.halfastack.entities.Book;

@RequestScoped
@Named
public class FacesController {
	
	private String name;
	private String result;
	private String method;
	private List<Book> books;
	private Logger log = LoggerFactory.getLogger(this.getClass());

	public FacesController() {
		result = "";
	}
	

	@Inject
	BookDB controller;
	
	public void find() {
		if(name != null) {
			books = controller.find(name, method);
			log.info("Setting books: {}",books);
			result = books.size() > 0 ? "" : "Your book/author was not. Try a different one.";
		} else {
			result = "Enter an author or a title please.";
		}
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
