package com.springboot.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.application.config.UserConfig;
import com.springboot.application.config.UserToken;
import com.springboot.application.dto.Labeldto;
import com.springboot.application.model.Label;
import com.springboot.application.model.Note;
import com.springboot.application.model.UserInfo;
import com.springboot.application.repositry.NoteRepository;
import com.springboot.application.repositry.UserRepo;
@Service
public class LabelServiceImpl implements LabelService{
	@Autowired
	private UserToken usertoken;
	@Autowired
	private UserRepo userrepo;
	@Autowired
	private NoteRepository noterepo;
	@Autowired
	private UserConfig userconfig;


	@Override
	public boolean createlabel(String token,long noteId, Labeldto dto) {
		long id = usertoken.parseToken(token);
		 UserInfo userInfo =userrepo.findbyId(id);
		 Note noteinfo=noterepo.findbyId(noteId);
		if (userInfo!=null&& noteinfo!=null)
		{
			Label info = userconfig.modelMapper().map(dto, Label.class);
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
			Label labelinfo=userconfig.modelMapper().map(dto,Label.class);
			labelinfo.setLabelId(id);
			boolean check=noterepo.updatelabel(labelinfo);
			if(check)
			{	
				return true;
			}
		}
		
		return false;
	
	}

	@Override
	public List<Label> getlabels(String token) {
		String token1 = token.substring(1, token.length() - 1);
		System.out.println(token1);
		long id=usertoken.parseToken(token1);
		
		List<Label>labels=noterepo.getlabel(id);
		return labels;
	}

	@Override
	public boolean labelcreation(String token, Labeldto dto) {
		String token1=token.substring(1,token.length()-1);
		
		long id=usertoken.parseToken(token1);
		UserInfo userInfo =userrepo.findbyId(id);
		
		if (userInfo!=null)
		{
			Label info = userconfig.modelMapper().map(dto, Label.class);
            userInfo.getGetManynotes().add(info);
            boolean check=noterepo.createlabel(info);
            if(check)
		{
			return true;
		}
	}
		return false;
}

	@Override
	public boolean addnotelabel(long labelid, long noteid, String token) {
		String token1=token.substring(1,token.length()-1);
		long id=usertoken.parseToken(token1);
		UserInfo user=userrepo.findbyId(id);
		if(user!=null)
		{	
		int check=noterepo.addlabelnote(labelid, noteid);
		System.out.println("inside addnotelabel.........."+check);
		if (check>0) {
			return true;
		}
		}
		return false;
	}

	@Override
	public boolean removenotelabel(long labelid, long noteid, String token) {
		String token1=token.substring(1,token.length()-1);
		long id=usertoken.parseToken(token1);
		UserInfo user=userrepo.findbyId(id);
		if(user!=null)
		{	
		int check=noterepo.removelabelnote(labelid, noteid);
		System.out.println("inside addnotelabel.........."+check);
		if (check>0) {
			return true;
		}
		}
		return false;
		
	}

	@Override
	public List<Label> getnote(long labelid, String token) {
		String token1=token.substring(1,token.length()-1);
		long id=usertoken.parseToken(token1);
		UserInfo user=userrepo.findbyId(id);
		if(user!=null)
		{
			List<Label> check=noterepo.findnote(labelid);
			for(Label note:check)
			{
				System.out.println(note.getName());
			}
			System.out.println(check.size());
			
			System.out.println(check);
			return check;
		}
		return null;
	}
	}
