package com.halfastack.db;

import java.util.Collections;
import java.util.List;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.halfastack.entities.Book;

@Singleton
public class BookDB {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@PersistenceContext
	EntityManager em;
	
	
	public List<Book> find(String name, String method) {
		switch(method) {
			case "author":
				return findByAuthor(name);
			case "title":
				return findByTitle(name);
			default:
				return Collections.emptyList();
		}
	}
	
	
	public List<Book> findByAuthor(String name) {
		TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE lower(b.author) LIKE CONCAT('%',?1,'%')", Book.class);
		query.setParameter(1, name.toLowerCase());
		return query.getResultList();
	}
	
	public List<Book> findByTitle(String title) {
		TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE lower(b.title) LIKE CONCAT('%',?1,'%')", Book.class);
		query.setParameter(1, title.toLowerCase());
		return query.getResultList();
	}
}
