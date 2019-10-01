package com.springboot.application.service;

import java.util.List;

import com.springboot.application.dto.Labeldto;
import com.springboot.application.model.Label;

public interface LabelService {

	public boolean createlabel(String token,long id,Labeldto dto);
	
	public boolean deletelabel(String token,long id);
	
	public boolean updatelabel(long id,String token,Labeldto dto);

	public List<Label> getlabels(String token);
}
