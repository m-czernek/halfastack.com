package com.halfastack.tests;

import javax.ws.rs.core.Response;

import org.hamcrest.core.IsEqual;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AuthorRestTests  {
	private static String PORT = "800";
	private static String HOST = "localhost";


	private void checkServer() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target("http://"+HOST+":"+PORT);
		Response response = target.request().get();
		Assert.assertThat("Your server is unreachable. Either turn on your server, "
				+ "or change PORT and HOST variables", response.getStatus(),IsEqual.equalTo(200));
	}
	
	@Test(timeout=1000)
	public void test() {
		checkServer();
		// TODO: Tests here
	}
	
	
	
}
