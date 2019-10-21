package com.springboot.application.exceptions;

public class Response {
private String subject;
private int statuscode;
private Object object;



public Response(String subject, int statuscode, Object object) {
	super();
	this.subject = subject;
	this.statuscode = statuscode;
	this.object = object;
}

public Object getObject() {
	return object;
}

public void setObject(Object object) {
	this.object = object;
}

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
