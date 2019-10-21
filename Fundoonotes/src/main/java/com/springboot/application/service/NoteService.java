package com.springboot.application.service;

import java.util.List;

import com.springboot.application.dto.Notedto;
import com.springboot.application.model.Note;

public interface NoteService {

	public boolean createnote(String token, Notedto dto);

	public boolean deletenote(String token, long id);

	public boolean updatenote(long id,String token, Notedto dto);

	public List<Note> getnotes(String token);
	
	public List<Note> searchnote(String token,String title);
	public boolean getarchivednotes(long id,String token);

	List<Note> getarchivenote();

	public boolean updatepin(long id, String token);

	public List<Note> getpinnote();

	public boolean updatetrash(long id, String token);

	public List<Note> gettrashnote();
	
	
}
