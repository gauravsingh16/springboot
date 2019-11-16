package com.springboot.application.model;

public class Login {

	private String token;
	private String email;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Login(String token, String email) {
		super();
		this.token = token;
		this.email = email;
	}
	public Login() {}
}
