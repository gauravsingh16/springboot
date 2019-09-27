package com.springboot.application.repositry;

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
	
}
