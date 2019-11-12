package com.springboot.application.repositry;

import java.util.List;

import com.springboot.application.model.UserInfo;

public interface UserRepo {
	public List<UserInfo> getallusers();
	
	public List<UserInfo> getuser(long id) ;
	public UserInfo dologin(String email);
	public List<UserInfo> sendemail(String email);
	public boolean save(UserInfo info);
	public void isverify(long id);
	public UserInfo forgetpassword(String email);
	public boolean changepassword(long id,String password);
	public UserInfo findbyId(long id);
	public UserInfo findbyemail(String email);
}
