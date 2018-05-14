package com.halfastack.tests;

import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.BeforeClass;
import org.junit.Test;


public class AuthorRestTests  {
	private static String PORT = "8080";
	private static String HOST = "localhost";
	private static String ENDPOINT_BASE = "http://"+HOST+":"+PORT;
	private static String ENDPOINT_APP_BASE = ENDPOINT_BASE + "/JAX_RS_1/api";
	private static ResteasyClient client;

	@BeforeClass
	public static void checkServer() {
		System.out.println("Running");
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
	
	@Test(timeout=1000l)
	public void testGetAuthors() {
		String targetEndpoint = ENDPOINT_APP_BASE+"/authors/getAuthors";
		WebTarget target = client.target(targetEndpoint);
	    List<String> response = target.request().get(List.class);
	    Assertions.assertThat(response)
	    	.as("Response did not contain author Amy Chua")
	    	.contains("Amy Chua")
	    	.as("Response did not contain Kazuo Ishiguro")
	    	.contains("Kazuo Ishiguro");
	}
	
	@Test(timeout=1000l)
	public void testGetAuthorByName() {
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
	
}
