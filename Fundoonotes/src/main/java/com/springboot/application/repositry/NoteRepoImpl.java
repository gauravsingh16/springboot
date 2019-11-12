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
		currentsession.saveOrUpdate(note);
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
	public boolean updatenote(long id) {
		Session currentsession = entity.unwrap(Session.class);
		currentsession.createQuery("update from Note where where id='"+id+"'"	).executeUpdate();
		//currentsession.saveOrUpdate(note);	
		return true;
	}
	@Transactional
	@Override
	public boolean createlabel(Label label) {
		System.out.println(label.getName());
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
	public Label findlabelbyId(long id) {
		Session currentsession = entity.unwrap(Session.class);
		Query query1 = currentsession.createQuery("from Label where labelId=:id");
		query1.setParameter("id",id);
		return (Label) query1.uniqueResult() ;
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
		List<Note> notes=currentsession.createQuery("from Note where user_id='"+userid+"'and archive=false and pin=false and trash=false").getResultList();
		return notes;
	}
	@Override
	@Transactional
	public List<Label> getlabel(long userid) {
		System.out.println(userid);
		Session currentsession=entity.unwrap(Session.class);
		List<Label> labels=currentsession.createQuery("from Label where userId='"+userid+"'").list();
		System.out.println(labels.toString());
		currentsession.close();
		return labels;
	}
	@Override
	public List<Note> getnotebytitle(long id,String title) {
		Session currentsession=entity.unwrap(Session.class);
		List<Note> notes=currentsession.createQuery("from Note where title=title").getResultList();
		return notes;
		
	}@Override
	@Transactional
	public Note getnote(long id)
	{
		System.out.println("note id      "+id);
		Session currentsession=entity.unwrap(Session.class);
		return  (Note) currentsession.createQuery("from Note where note_id="+id).uniqueResult();
		
	}
 	
	@Override
	
	public List<Note> getarchivenote(long id) {
		System.out.println("in repo");
		Session currentsession=entity.unwrap(Session.class);
		List<Note>notes=currentsession.createQuery("from Note where user_id='"+id+"' and archive=true and pin=false and trash=false").getResultList();
		System.out.println(notes);
		return notes;
	}
	@Override
	
	public List<Note> getpinnote(long id) {
		Session currentsession=entity.unwrap(Session.class);
		List<Note>notes=currentsession.createQuery("from Note where user_id='"+id+"'and pin=true and trash=false and archive=false").getResultList();
		return notes;
	}
	@Override
	
	public List<Note> gettrashnote(long id) {
		Session currentsession=entity.unwrap(Session.class);
		List<Note>notes=currentsession.createQuery("from Note where user_id='"+id+"'and trash=true and pin=false and archive=false").getResultList();
		return notes;
		
	}
	@Override
	@Transactional
	public void addlabelnote(Label labelinfo) {
		Session currentsession=entity.unwrap(Session.class);
		currentsession.save(labelinfo);
		
	}
	@Override
	@Transactional
	public int removelabelnote(long labelid, long noteid) {
		Session currentsession=entity.unwrap(Session.class);
		 return currentsession.createQuery("update Label set note_id="+null+" where labelid="+labelid).executeUpdate();
		}
	@Override
	@Transactional
	public List<Label> findnote(long id) {
		System.out.println(id);
		Session currentsession=entity.unwrap(Session.class);
		 List<Label> note=currentsession.createQuery("from Label where labelid="+id).getResultList();
		 return note;
	}
	@Override
	public List<Note> getremindernote(long id) {
		System.out.println(id);
		Session currentsession=entity.unwrap(Session.class);
		 List<Note> note=currentsession.createQuery("from Note where user_id='"+id+"'and reminder!='"+null+"'").getResultList();
		 return note;
	}
	@Override
	public List<Note> getcollab(long id) {
		Session currentsession=entity.unwrap(Session.class);
		 List<Note> note=currentsession.createQuery("from Note where user_id='"+id+"'and reminder!='"+null+"'").getResultList();
		 return note;
	}
	
}
