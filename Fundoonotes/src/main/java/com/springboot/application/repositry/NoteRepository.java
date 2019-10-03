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
	public List<Label>gelabel(long id);
	public List<Note>getnotebytitle(String title);
}
