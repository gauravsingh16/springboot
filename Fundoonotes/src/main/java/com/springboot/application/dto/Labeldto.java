package com.springboot.application.dto;

import javax.persistence.Id;

import org.springframework.stereotype.Component;
@Component

public class Labeldto {
	@Id
	private long labelid;
private String name;
public long getLabelid() {
	return labelid;
}
public void setLabelid(long labelid) {
	this.labelid = labelid;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}

}
