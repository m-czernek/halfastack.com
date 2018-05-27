package com.halfastack.rest;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/jaas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class SecuredRestService {
	
	@RolesAllowed({"developer"})
	@GET
	@Path("")
	public String getHello() {
		return "{\"response\":\"Hello from JAAS\"}";
	}
}
