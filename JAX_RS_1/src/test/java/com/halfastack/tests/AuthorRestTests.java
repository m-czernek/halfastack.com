package com.halfastack.tests;

import org.assertj.core.api.Assertions;

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
	    String response = target.request().get().readEntity(String.class);
	    Assertions.assertThat(response)
	    	.as("Response did not contain author Amy Chua")
	    	.contains("Amy Chua");
	}
	
}
