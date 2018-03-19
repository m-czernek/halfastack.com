package com.halfastack.controllers;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named
public class FacesController {
	
	String name;
	String result;
	String method;

	public FacesController() {
		result = "";
	}
	

	@EJB
	DBController controller;
	
	public void find() {
		if(name != null) {
			result = controller.find(name, method);
		} else {
			result = "Enter name or title please";
		}
	}	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getResult() {
		return result;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}
