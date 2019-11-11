package com.springboot.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.application.config.UserToken;
import com.springboot.application.dto.Notedto;
import com.springboot.application.model.Note;
import com.springboot.application.model.UserInfo;
import com.springboot.application.repositry.NoteRepository;
import com.springboot.application.repositry.UserRepo;
@Service
public class NoteServiceImpl implements NoteService {
@Autowired
private UserToken usertoken;

@Autowired
private NoteRepository noterepo;
@Autowired
private UserRepo userrepo;

@Autowired
private ModelMapper mapper;
@Autowired
private ElasticService elasticservice;


	@Override
	public boolean createnote(String token, Notedto dto) {
		long id = usertoken.parseToken(token);
		System.out.println(dto.getTitle());
		System.out.println(id);
		 UserInfo userInfo =userrepo.findbyId(id);
		if (userInfo!=null)
		{
			Note info = mapper.map(dto, Note.class);
               info.setArchive(false);
               info.setPin(false);
               info.setCreatetime(LocalDateTime.now());
               info.setUpdatetime(LocalDateTime.now());
               userInfo.getNotes().add(info);
              
               boolean check = noterepo.createnote(info);
               if (check) {
            	   try {
            		   System.out.println(info);
       			String check1=elasticservice.CreateNote(info);
       			
       			System.out.println(check1);
       			} catch (Exception e) {
       				e.printStackTrace();
       			}
            	   return true;
               }
                 else {
					
               	  return false;
				}                
		}
       else {
			
     	  return false;
		}         
               
	}

	@Override
	public boolean deletenote(String token, long id) {
		System.out.println("inside service");
		long ids=usertoken.parseToken(token);
		UserInfo userinfo=userrepo.findbyId(ids);
		Object objectid=userinfo.getId();
		if(userinfo.getNotes().contains(objectid));
		{
			System.out.println("inside if");
		boolean check=noterepo.deletenote(id);
		if(check)
		{
			return true;
		}else
		{
			return false;
		}
		
	}
	}

	@Override
	public boolean updatenote(long id,String token, Notedto dto) {
		long ids=usertoken.parseToken(token);
		System.out.println(dto.getTitle());
		UserInfo userInfo=userrepo.findbyId(ids);
		if(userInfo!=null)
		{	System.out.println(userInfo.getId());
			Note notes=noterepo.findbyId(id);
			
			System.out.println(dto.getTitle());
			notes.setTitle(dto.getTitle());
			notes.setDesc(dto.getDesc());
			System.out.println(dto.getReminder());
			notes.setReminder(dto.getReminder());
			notes.setUpdatetime(LocalDateTime.now());
			
			System.out.println(notes.getTitle());
			boolean check=noterepo.createnote(notes);
			if(check)
			{	
				return true;
			}
		}
		
		return false;
	
	}
	@Override
	public List<Note> getnotes(String token) {
		
		System.out.println(token);
		long id=usertoken.parseToken(token);
		UserInfo userInfo=userrepo.findbyId(id);
		if(userInfo!=null) {
			System.out.println(userInfo);
			List<Note>notes=noterepo.getnotes(id);
			notes.stream().filter(data->data.isArchive()==false && !data.isTrash() && !data.isPin()).collect(Collectors.toList());
			System.out.println(notes.size());
			return notes;
		}
		return null;
	}

	@Override
	public List<Note> searchnote(String token,String title) {
		String token1=token.substring(1,token.length()-1);
		long id=usertoken.parseToken(token1);
		List<Note> notes=noterepo.getnotebytitle(id,title);
		if(notes.size()>0)
		{	List<Note>notes1=elasticservice.searchbytitle(title);
			return notes1;
		}
		return null;
	
	}

	@Override
	public boolean archivednotes(long id,String token) {
		long id1=usertoken.parseToken(token);
		if(userrepo.findbyId(id1)!=null) {
			Note note=noterepo.getnote(id);
			if(note!=null)
			{
				note.setArchive(!note.isArchive());
				noterepo.createnote(note);
				return true;
			}
		}		return false;
	}
	@Override
	public List<Note> getarchivenote(String token)
	{
		long id=usertoken.parseToken(token);
		if(userrepo.findbyId(id)!=null)
		{
			List<Note> notes=noterepo.getarchivenote(id);
			return notes;
		
		}
		return null;
	}

	@Override
	public boolean updatepin(long id, String token) {
		long id1=usertoken.parseToken(token);
		if(userrepo.findbyId(id1)!=null) {
			Note note=noterepo.getnote(id);
			if(note!=null)
			{
				note.setPin(!note.isPin());
				noterepo.createnote(note);
				return true;
			}
		}		return false;
		
	}

	@Override
	public List<Note> getpinnote(String token) {
		long id=usertoken.parseToken(token);
		if(userrepo.findbyId(id)!=null)
		{
			List<Note> notes=noterepo.getpinnote(id);
			return notes;
		
		}
		return null;
	}

	@Override
	public boolean updatetrash(long id, String token) {
		long id1=usertoken.parseToken(token);
		if(userrepo.findbyId(id1)!=null) {
			System.out.println("in if");
			Note note=noterepo.getnote(id);
			
			System.out.println(note.getId());
			if(note!=null)
			{	note.setTrash(!note.isTrash());
				noterepo.createnote(note);
				return true;
			}
		}		return false;
	}

	@Override
	public List<Note> gettrashnote(String token) {
		long id=usertoken.parseToken(token);
		if(userrepo.findbyId(id)!=null)
		{
			List<Note> notes=noterepo.gettrashnote(id);
			System.out.println("with trash");
			return notes;
		}
		System.out.println("without trash");
		return null;
	}

	@Override
	public boolean doreminder(long id,Notedto dto, String token) {
		long ids=usertoken.parseToken(token);
		if(userrepo.findbyId(ids)!=null)
		{
			Note noteinfo= noterepo.findbyId(id);
			noteinfo.setReminder(dto.getReminder());
			if(noterepo.createnote(noteinfo)) {
				System.out.println("with reminder");
				return true;	
			}
			
		}
		System.out.println("without reminder");
			
		return false;
	}

	@Override
	public boolean removereminder(long id, String token) {
		long ids=usertoken.parseToken(token);
		if(userrepo.findbyId(ids)!=null)
		{
			Note noteinfo= noterepo.findbyId(id);
			noteinfo.setReminder(null);
			if(noterepo.createnote(noteinfo)) {
				System.out.println("with reminder");
				return true;	
			}
			
		}
		System.out.println("without reminder");
			
		return false;
	
		
	}

	@Override
	public List<Note> remindernotes(String token) {
		long ids=usertoken.parseToken(token);
		if(userrepo.findbyId(ids)!=null)
		{
			List<Note>notes=noterepo.getremindernote(ids);
			if(notes.size()>0)
			{
				return notes;
			}
			return null;
		}
		return null;
	}

	@Override
	public boolean changecolor(long id, Notedto dto, String token) {
		long ids=usertoken.parseToken(token);
		if(userrepo.findbyId(ids)!=null)
		{
			Note noteinfo= noterepo.findbyId(id);
			noteinfo.setColor(dto.getColor());
			if(noterepo.createnote(noteinfo)) {
				System.out.println("with color");
				return true;	
			}
			
		}
		System.out.println("without color");
			
		return false;
	}
}