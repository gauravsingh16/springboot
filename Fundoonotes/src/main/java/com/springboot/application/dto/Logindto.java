package com.springboot.application.dto;

import org.springframework.stereotype.Component;

@Component
public class Logindto {
private String email;
private String password;

public Logindto() {}
public Logindto(String email, String password) {
}
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

}
