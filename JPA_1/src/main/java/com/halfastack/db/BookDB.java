package com.halfastack.db;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.halfastack.entities.Book;

@Singleton
public class BookDB {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext
	EntityManager em;
	
	
	
	public String findByAuthor(String name) {
		
		log.info("am I null?");
		log.info(Boolean.toString(em == null));
		Book b = em.find(Book.class, 1L);
		log.info(b.toString());
		return "not implemented";
	}
	
	public String findByTitle(String name) {
		return "not implemented";
	}
}
