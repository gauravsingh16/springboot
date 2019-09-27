package com.springboot.application.model;

public class Response {
private String subject;
private int statuscode;

public String getSubject() {
	return subject;
}

public void setSubject(String subject) {
	this.subject = subject;
}

public int getStatuscode() {
	return statuscode;
}

public void setStatuscode(int statuscode) {
	this.statuscode = statuscode;
}

public Response(String subject, int statuscode) {
	super();
	this.subject = subject;
	this.statuscode = statuscode;
}


}
