package com.halfastack.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.halfastack.entities.Author;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class AuthorEntityRestService {
	
	@PersistenceContext
	EntityManager em;

	
	/**
	 * Get all author names saved in the database
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
	
	
}
