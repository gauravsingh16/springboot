package com.springboot.application.repositry;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springboot.application.model.Label;
import com.springboot.application.model.Note;
@Repository
public class NoteRepoImpl implements NoteRepository  {
@Autowired
private EntityManager entity;

	@Transactional
	@Override
	public boolean createnote(Note note) {
		Session currentsession=entity.unwrap(Session.class);
		currentsession.save(note);
		return true;
	}
	@Transactional
	@Override
	public boolean deletenote(long id) {
		System.out.println(id);
		Session currentsession = entity.unwrap(Session.class);
		Query query1 = currentsession.createQuery("delete from Note where id=:id");
		query1.setParameter("id", id);
		query1.executeUpdate();
		return true;
	}
	
	@Transactional
	@Override
	public boolean updatenote(Note note) {
		Session currentsession = entity.unwrap(Session.class);
		System.out.println(note.getId());
		 currentsession.saveOrUpdate(note);
		
		return true;
	}
	@Transactional
	@Override
	public boolean createlabel(Label label) {
		Session currentsession=entity.unwrap(Session.class);
		currentsession.save(label);
		return true;
	}
	@Override
	@Transactional
	public Note findbyId(long id) {
		Session currentsession = entity.unwrap(Session.class);
		Query query1 = currentsession.createQuery("from Note where noteId=:id");
		query1.setParameter("id",id);
		return (Note) query1.uniqueResult() ;
	}
	@Override
	@Transactional
	public boolean deletelabel(long id) {
		System.out.println(id);
		Session currentsession = entity.unwrap(Session.class);
		Query query1 = currentsession.createQuery("delete from Label where id=:id");
		query1.setParameter("id", id);
		query1.executeUpdate();
		return true;
	}
	@Override
	@Transactional
	public boolean updatelabel(Label label) {
		Session currentsession = entity.unwrap(Session.class);
		System.out.println(label.getLabelId());
		 currentsession.saveOrUpdate(label);
		
		return true;
	}
	@Override
	@Transactional
	public List<Note> getnotes(long userid) {
		Session currentsession = entity.unwrap(Session.class);
		List<Note> notes=currentsession.createQuery("from Note where userid=:userid").getResultList();
		return notes;
	}
	@Override
	@Transactional
	public List<Label> gelabel(long userid) {
		Session currentsession=entity.unwrap(Session.class);
		List<Label> labels=currentsession.createQuery("from Note where userid=userid").getResultList();
		return labels;
	}
	@Override
	public List<Note> getnotebytitle(String title) {
		Session currentsession=entity.unwrap(Session.class);
		List<Note> notes=currentsession.createQuery("from Note where title=title").getResultList();
		return notes;
		
	}
}
