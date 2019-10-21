package com.springboot.application.repositry;

import java.util.List;

import com.springboot.application.model.Label;
import com.springboot.application.model.Note;

public interface NoteRepository {
	public boolean createnote(Note note);
	public boolean deletenote(long id);
	public boolean updatenote(Note note);
	public boolean createlabel(Label label);
	public Note findbyId(long id);
	public boolean deletelabel(long id);
	public boolean updatelabel(Label label);
	public List<Note>getnotes(long id);
	public List<Label>getlabel(long id);
	public List<Note>getnotebytitle(long id,String title);
	
	public List<Note>getarchivenote();
	Note getnote(long id);
	public List<Note> getpinnote();
	public List<Note> gettrashnote();
	public int addlabelnote(long labelid, long noteid);
	public int removelabelnote(long labelid, long noteid);
	public List<Label> findnote(long id);
}
