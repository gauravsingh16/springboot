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
             info.setUserId(id);
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
			labelinfo.setUserId(ids);
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
		System.out.println(token);
		long id=usertoken.parseToken(token);
		UserInfo userInfo=userrepo.findbyId(id);
		//if(userInfo!=null)
		List<Label>labels=noterepo.getlabel(id);
		System.out.println("asdad");
		return labels;
		
	}

	@Override
	public boolean labelcreation(String token, Labeldto dto) {
		
		long id=usertoken.parseToken(token);
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
		long id=usertoken.parseToken(token);
		UserInfo user=userrepo.findbyId(id);
		if(user!=null)
		{	
		Label labelinfo=noterepo.findlabelbyId(labelid);
		Note noteinfo=noterepo.findbyId(noteid);
		labelinfo.getNote().add(noteinfo);
		noterepo.addlabelnote(labelinfo);
		}
		return true;
	}

	@Override
	public boolean removenotelabel(long labelid, long noteid, String token) {
		long id=usertoken.parseToken(token);
		UserInfo user=userrepo.findbyId(id);
		if(user!=null)
		{	
	    Note note=noterepo.getnote(noteid);
		Label labelinfo=noterepo.findlabelbyId(labelid);
		note.getLabel().remove(labelinfo);
		noterepo.createnote(note);
		//labelinfo.getNote().clear();
//		System.out.println("inside addnotelabel.........."+check);
//		if (check>0) {
//			return true;
//		}
		}
		return true;
		
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
