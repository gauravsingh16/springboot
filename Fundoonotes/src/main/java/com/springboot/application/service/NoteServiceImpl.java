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
       				elasticservice.CreateNote(info);
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
	public List<Note> getnotes(String token) {
		long id=usertoken.parseToken(token);

		List<Note>notes=noterepo.getnotes(id);
		notes.stream().sorted((e1,e2)->e2.getCreatetime().compareTo(e1.getCreatetime())).collect(Collectors.toList());
		return notes;
	}

}
