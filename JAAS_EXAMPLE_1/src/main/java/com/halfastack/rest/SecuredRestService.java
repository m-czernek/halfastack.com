package com.halfastack.rest;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/jaas")
@Stateless
public class SecuredRestService {
	
	@RolesAllowed({"developer"})
	@GET
	@Path("/getDeveloperHello")
	public String getDeveloperHello() {
		return "Hello for developers";
	}
	
	@RolesAllowed("guest")
	@GET
	@Path("/getGuestOnlyHello")
	public String getGuestExclusiveHello() {
		return "Hello for guests";
	}
	
	@PermitAll
	//@RolesAllowed({"guest","developer"})
	@GET
	@Path("/getHello")
	public String getHello() {
		return "Hello for guests and developer";
	}
	
	
}
