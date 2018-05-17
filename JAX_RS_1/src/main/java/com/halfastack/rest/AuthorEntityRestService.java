package com.halfastack.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.halfastack.entities.Author;
import com.halfastack.entities.Book;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class AuthorEntityRestService {
	
	@PersistenceContext
	EntityManager em;
	
	/**
	 * Get all author names saved in the database
	 * 
	 * @return A JSON array of names
	 */
	@GET
	@Path("/getAuthors")
	public List<Object> getAuthors() {
		Query q = em.createQuery("SELECT concat(a.firstName,' ',a.surname) as authorName FROM Author a");
		return q.getResultList();
	}
	
	/**
	 * Get all author information by name. Partial matching is supported, e.g. Ant will match Anthony, antony, or Mantis
	 * 
	 * @param firstName First name of the author. Default value is an empty string (all authors with any first name)
	 * @param secondName Surname of the author. Default value is an empty string (all authors with any surname)
	 * @return List of authors
	 */
	@GET
	@Path("/getAuthorByName")
	public List<Author> getAuthorByName(@DefaultValue("") @QueryParam("firstName") String firstName, 
			@DefaultValue("") @QueryParam("secondName") String secondName) {
		
		TypedQuery<Author> q = em.createQuery("SELECT a FROM Author a JOIN FETCH a.books"
				+ " WHERE (lower(a.firstName) LIKE CONCAT('%',?1,'%'))"
				+ " AND (lower(a.surname) LIKE CONCAT('%',?2,'%'))", Author.class);
		q.setParameter(1, firstName.toLowerCase());
		q.setParameter(2, secondName.toLowerCase());
		return q.getResultList();
	}
	
	/**
	 * Gets author by its ID (database PK)
	 * 
	 * @param id The primary key
	 * @return Author object
	 */
	@GET
	@Path("/getAuthorById/{id:[0-9+]}")
	public Author getAuthorById(@PathParam("id") long id) {
		TypedQuery<Author> q = em.createQuery("SELECT a from Author a JOIN FETCH a.books"
				+ " WHERE a.id = ?1",Author.class);
		q.setParameter(1, id);
		Author result;
		try {
			result = q.getSingleResult();
		} catch (NoResultException e) {
			result = null;
		}
		return result;
	}
	
	/**
	 *  Creates a new Author entity and saves it into the Database. Note that 
	 *  this Author entity has no books associated with it; as a consequence,
	 *  it will not appear in any call that fetches the book list if no Book
	 *  entity has been assigned to it.
	 *  
	 * @param author The Author entity to save
	 */
	@POST
	@Path("/createAuthor")
	public void createAuthor(Author author) {
		em.persist(author);
	}
	
	/**
	 *  Updates an Author entity and saves it into the Database. Note that 
	 *  if this Author entity does not exist, it will be created
	 *  
	 * @param author The Author entity that will update an old entity, or create
	 *  a new one
	 */
	@PUT
	@Path("/updateAuthor")
	public void updateAuthor(Author author) {
		em.merge(author);
	}
	
	
	/**
	 * Deletes author by ID (database PK)
	 * 
	 * @param id The primary key
	 */
	@DELETE
	@Path("/deleteAuthor/{id:[0-9+]}")
	public void deleteAuthor(@PathParam("id") long id) {
		Author author = em.find(Author.class, id);
		for(Book book : author.getBooks()) {
			// Book can have only one author; we remove it since each entity 
			// belongs to strictly one author
			em.remove(book);
		}
		// Author is safe to be removed now, our DB is consistent
		em.remove(author);
	}
	
	@GET
	@Path("getLastAuthorId")
	public Long getLastAuthorId() {
		return em.createQuery("SELECT max(a.id) FROM Author a", Long.class).getSingleResult();
	}
	
	
	
}
