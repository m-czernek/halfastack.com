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
	
	@PersistenceContext
	EntityManager em;
	
	
	public List<Book> find(String firstName, String surname, String title, String method) {
		switch(method) {
			case "author":
				return findByAuthor(firstName, surname);
			case "title":
				return findByTitle(title);
			default:
				return Collections.emptyList();
		}
	}
	
	
	public List<Book> findByAuthor(String firstName, String surname) {
		TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE (lower(b.author.firstName) LIKE CONCAT('%',?1,'%')) AND (lower(b.author.surname) LIKE CONCAT('%',?2,'%') )", Book.class);
		query.setParameter(1, firstName.toLowerCase());
		query.setParameter(2, surname.toLowerCase());
		return query.getResultList();
	}
	
	public List<Book> findByTitle(String title) {
		TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b WHERE lower(b.title) LIKE CONCAT('%',?1,'%')", Book.class);
		query.setParameter(1, title.toLowerCase());
		return query.getResultList();
	}
}
