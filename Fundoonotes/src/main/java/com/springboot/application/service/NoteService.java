package com.springboot.application.service;

import com.springboot.application.dto.Labeldto;
import com.springboot.application.dto.Notedto;

public interface NoteService {

public boolean createnote(String token,Notedto dto);
	
	public boolean deletenote(String token,long id);

	public boolean updatenote(long id,String token,Notedto dto);
	
	public boolean createlabel(String token,long id,Labeldto dto);
	
	public boolean deletelabel(String token,long id);
	
	public boolean updatelabel(long id,String token,Labeldto dto);
}
