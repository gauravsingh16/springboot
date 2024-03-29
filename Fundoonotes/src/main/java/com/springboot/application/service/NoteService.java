package com.springboot.application.service;

import java.util.List;

import com.springboot.application.dto.Notedto;
import com.springboot.application.model.Note;
import com.springboot.application.model.UserInfo;

public interface NoteService {

	public boolean createnote(String token, Notedto dto);

	public boolean deletenote(String token, long id);

	public boolean updatenote(long id,String token, Notedto dto);

	public List<Note> getnotes(String token);
	
	public List<Note> searchnote(String token,String title);
	public boolean archivednotes(long id,String token);

	List<Note> getarchivenote(String token);

	public boolean updatepin(long id, String token);

	public List<Note> getpinnote(String token);

	public boolean updatetrash(long id, String token);

	public List<Note> gettrashnote(String token);

	public boolean doreminder(long id,Notedto dto, String token);

	public boolean removereminder(long id, String token);

	public List<Note> remindernotes(String token);

	public boolean changecolor(long id, Notedto dto, String token);

	public boolean doCollab(long id, String email, String token);

	public List<UserInfo> getCollab(long id, String token);

	public boolean deleteCollab(long noteid, long userid, String token);

	
	
}
