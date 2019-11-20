package com.springboot.application.service;

import java.util.List;

import com.springboot.application.dto.Logindto;
import com.springboot.application.dto.Notedto;
import com.springboot.application.dto.Registerdto;
import com.springboot.application.model.Login;
import com.springboot.application.model.UserInfo;

public interface UserService {
	public List<UserInfo> getallusers();

	public List<UserInfo> getuser(String email);

	public Login dologin(Logindto user);

	public boolean sendmail(Logindto user);

	public boolean save(Registerdto dto,String queue);
	
	
	public boolean verify(String token);
	
	public boolean forgetpassword(Logindto user);
	
	public boolean changepassword(long id,Logindto user);

	List<UserInfo> getuserbyemail(String token);

	public boolean updateUser(Registerdto user, String token);
	
}
