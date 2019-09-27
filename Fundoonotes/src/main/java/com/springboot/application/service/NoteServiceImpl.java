package com.springboot.application.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.application.config.UserToken;
import com.springboot.application.dto.Labeldto;
import com.springboot.application.dto.Notedto;
import com.springboot.application.model.Label;
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
	@Override
	public boolean createnote(String token, Notedto dto) {
		long id = usertoken.parseToken(token);
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
	public boolean updatenote(long id, String token, Notedto dto) {
		long ids=usertoken.parseToken(token);
		UserInfo userInfo=userrepo.findbyId(ids);
		if(userInfo!=null)
		{	System.out.println(userInfo.getId());
			Note notes=mapper.map(dto,Note.class);
			notes.setId(id);
			notes.setUpdatetime(LocalDateTime.now());
			
			boolean check=noterepo.updatenote(notes);
			if(check)
			{	
				return true;
			}
		}
		
		return false;
	
	}

	@Override
	public boolean createlabel(String token,long noteId, Labeldto dto) {
		long id = usertoken.parseToken(token);
		 UserInfo userInfo =userrepo.findbyId(id);
		 Note noteinfo=noterepo.findbyId(noteId);
		if (userInfo!=null&& noteinfo!=null)
		{
			Label info = mapper.map(dto, Label.class);
             userInfo.getGetManynotes().add(info);
             noteinfo.getLabel().add(info);
              boolean check = noterepo.createlabel(info);
              if (check) {
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
	public boolean deletelabel(String token, long id) {
		System.out.println("inside service");
		long ids=usertoken.parseToken(token);
		UserInfo userinfo=userrepo.findbyId(ids);
		Note noteinfo=noterepo.findbyId(id);
		Object objectuserid=userinfo.getId();
		Object objectnoteid=noteinfo.getId();
		if(userinfo.getGetManynotes().contains(objectuserid)&& noteinfo.getLabel().contains(objectnoteid));
		{
			System.out.println("inside if");
		boolean check=noterepo.deletelabel(id);
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
	public boolean updatelabel(long id, String token, Labeldto dto) {
		long ids=usertoken.parseToken(token);
		UserInfo userInfo=userrepo.findbyId(ids);
		if(userInfo!=null)
		{	System.out.println(userInfo.getId());
			Label labelinfo=mapper.map(dto,Label.class);
			labelinfo.setLabelId(id);
			boolean check=noterepo.updatelabel(labelinfo);
			if(check)
			{	
				return true;
			}
		}
		
		return false;
	
	}

}
