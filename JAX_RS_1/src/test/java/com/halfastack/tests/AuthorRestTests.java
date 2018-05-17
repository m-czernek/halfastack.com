package com.halfastack.tests;

import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.halfastack.entities.Author;


public class AuthorRestTests  {
	private static String PORT = "8080";
	private static String HOST = "localhost";
	private static String ENDPOINT_BASE = "http://"+HOST+":"+PORT;
	private static String ENDPOINT_APP_BASE = ENDPOINT_BASE + "/JAX_RS_1/api";
	private static ResteasyClient client;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@BeforeClass
	public static void checkServer() {
		client = new ResteasyClientBuilder()
				.build();
		
		Future<Response> resp = null;
		
		try {
			WebTarget target = client.target(ENDPOINT_BASE);
			resp = target.request().async().get();
			Assertions.assertThat(resp.get(1000, TimeUnit.MILLISECONDS).getStatus())
				.as("Server did not respond with acceptable code 200")
				.isEqualTo(Response.Status.OK.getStatusCode());
			
		} catch (Exception e) {
			e.printStackTrace();
			resp.cancel(true);
			Assertions.fail("Your server is not running, or is not accessible on: " + HOST + ":" + PORT + ". "
					+ "Either turn on your server, or change PORT and HOST variables");
		}
	}
	
	@Test(timeout = 5000l)
	public void testAll() {
		log.info("Testing /getAuthors endpoint.");
		testGetAuthors();
		log.info("endpoint tested.");
		log.info("---------------------");
		
		log.info("Testing /getAuthorByName endpoint");
		testGetAuthorByName();
		log.info("endpoint tested.");
		log.info("---------------------");
		
		log.info("Testing /createAuthor endpoint");
		testCreateAuthor();
		log.info("Verifying whether author has been added.");
		boolean isInDatabase = doesAuthorExist("Lady May");
		Assertions.assertThat(isInDatabase)
			.as("Author has not been saved")
			.isTrue();
		log.info("Author has been added successfully.");
		log.info("---------------------");
		
		log.info("Testing /updateAuthor endpoint");
		testUpdateAuthor();
		log.info("Author has been updated");
		log.info("---------------------");
		
		log.info("Testing /deleteAuthor endpoint");
		testDeleteAuthor();
		log.info("Verifying author is no longer in the database");
		isInDatabase = doesAuthorExist("Lady May");
		Assertions.assertThat(isInDatabase)
			.as("Lady May has not been deleted from the database")
			.isFalse();
		log.info("Author has been deleted");
		log.info("---------------------");
	}
	
	private void testGetAuthors() {
		String targetEndpoint = ENDPOINT_APP_BASE+"/authors/getAuthors";
		WebTarget target = client.target(targetEndpoint);
	    List<String> response = target.request().get(List.class);
	    Assertions.assertThat(response)
	    	.as("Response did not contain author Amy Chua")
	    	.contains("Amy Chua")
	    	.as("Response did not contain Kazuo Ishiguro")
	    	.contains("Kazuo Ishiguro");
	}
	
	private boolean doesAuthorExist(String name) {
		String targetEndpoint = ENDPOINT_APP_BASE+"/authors/getAuthors";
		WebTarget target = client.target(targetEndpoint);
	    List<String> response = target.request().get(List.class);
	    log.warn("{}", response);
	    return response.contains(name);
	}
	
	private void testGetAuthorByName() {
		String targetEndpoint = ENDPOINT_APP_BASE+"/authors/getAuthorByName";
		WebTarget target = client.target(targetEndpoint)
				.queryParam("firstName", "Amy")
				.queryParam("secondName", "Chua");
		
		try {
			List<Map<String, Object>> authors = target.request().get(List.class);
			Map<String, Object> amy = authors.get(0);
			
			Assertions.assertThat(amy.get("firstName"))
				.as("We expect the first Name to be Amy")
				.isEqualTo("Amy");
			Assertions.assertThat(amy.get("surname"))
				.as("We expect surname to be Chua")
				.isEqualTo("Chua");
			
		} catch (Exception e) {
			e.printStackTrace();
			Assertions.fail("Return type was not a list of maps that map to an author entity");
		}
	}
	
	private void testCreateAuthor() {
		Author author = new Author();
		author.setFirstName("Lady");
		author.setSurname("May");
		
		String targetEndpoint = ENDPOINT_APP_BASE+"/authors/createAuthor";
		WebTarget target = client.target(targetEndpoint);
		target.request().post(Entity.entity(author, MediaType.APPLICATION_JSON));
	}
	
	private void testUpdateAuthor() {
		String targetEndpoint = ENDPOINT_APP_BASE+"/authors/getAuthorById/1";
		WebTarget target = client.target(targetEndpoint);
		Author mariko = null;
		
		try {
			mariko = target.request().get(Author.class);

			Assertions.assertThat(mariko.getFirstName())
				.as("We expect the first Name to be Mariko")
				.isEqualTo("Mariko");
			Assertions.assertThat(mariko.getSurname())
				.as("We expect surname to be Koike")
				.isEqualTo("Koike");
			
		} catch(NullPointerException e) { 
			Assertions.fail("Mariko was not in the database");
		} catch (Exception e) {
			e.printStackTrace();
			Assertions.fail("Return type was not an author entity");
		}
		
		Author newMariko = new Author();
		newMariko.setId(1l);
		newMariko.setFirstName("Mariko");
		newMariko.setSurname("Tamaki");
		String targetEndpointUpdate = ENDPOINT_APP_BASE+"/authors/updateAuthor";
		WebTarget targetUpdate = client.target(targetEndpointUpdate);
		targetUpdate.request().put(Entity.entity(newMariko, MediaType.APPLICATION_JSON));
		
		Author updatedMariko = target.request().get(Author.class);
		Assertions.assertThat(updatedMariko.getSurname())
			.isEqualTo("Tamaki");
		
		// Return the database back to its original state
		targetUpdate = client.target(targetEndpointUpdate);
		targetUpdate.request().put(Entity.entity(mariko, MediaType.APPLICATION_JSON));
	}
	
	private void testDeleteAuthor() {
		long lastId = getLastId();
		String targetEndpoint = ENDPOINT_APP_BASE+"/authors/deleteAuthor/"+lastId ;
		WebTarget target = client.target(targetEndpoint);
		target.request().delete();
	}

	private long getLastId() {
		String targetEndpoint = ENDPOINT_APP_BASE+"/authors/getLastAuthorId";
		WebTarget target = client.target(targetEndpoint);
		return target.request().get(Long.class);
	}
	
}
