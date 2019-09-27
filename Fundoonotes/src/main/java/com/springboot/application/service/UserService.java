package com.springboot.application.service;

import java.util.List;

import com.springboot.application.dto.Logindto;
import com.springboot.application.dto.Notedto;
import com.springboot.application.dto.Registerdto;
import com.springboot.application.model.UserInfo;

public interface UserService {
	public List<UserInfo> getallusers();

	public long getid(String email);

	public String dologin(Logindto user);

	public boolean sendmail(Logindto user);

	public boolean save(Registerdto dto);
	
	
	public boolean verify(String token);
	
	public boolean forgetpassword(Logindto user);
	
	public boolean changepassword(String token ,String password);
	
}
