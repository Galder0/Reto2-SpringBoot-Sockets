package com.reto.elorchatS.Security.model;



import jakarta.validation.constraints.NotNull;

public class AuthRequest {
	@NotNull
	private String email;
	
	@NotNull
	private String password;
	
	private String name;
	
	private String surnames;
	
	private String DNI;
	
	private String direction;
	
	private String phone_number;
	
	private Boolean fct_dual;
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurnames() {
		return surnames;
	}

	public void setSurnames(String surnames) {
		this.surnames = surnames;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public Boolean getFct_dual() {
		return fct_dual;
	}

	public void setFct_dual(Boolean fct_dual) {
		this.fct_dual = fct_dual;
	}
	
	
	
	
	
}
