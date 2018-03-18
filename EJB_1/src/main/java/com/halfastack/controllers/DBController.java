package com.halfastack.controllers;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.halfastack.db.BookDB;

@Stateless
public class DBController {
	
	@Inject
	BookDB database;
	
	public String find(String name, String method) {
		switch(method) {
			case "author":
				return database.findByAuthor(name);
			case "title":
				return database.findByTitle(name);
			default:
				throw new RuntimeException("Unsupported option: "+ method);
		}
	}
}
