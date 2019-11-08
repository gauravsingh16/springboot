package com.springboot.application.service;

import java.util.List;

import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.application.config.UserToken;
import com.springboot.application.dto.Logindto;
import com.springboot.application.dto.Registerdto;
import com.springboot.application.model.UserInfo;
import com.springboot.application.repositry.UserRepo;

@Service
public class UserImpl implements UserService {
	private static final Logger LOGGER = Logger.getLogger(UserImpl.class.getName());
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private JavaMailSender mailsender;

	@Autowired
	private UserRepo userrepo;
	@Autowired
	private MessageReciever reciever;

	@Autowired
	private UserToken usertoken;

	@Override
	public long getid(String email) {
		// UserInfo userinfo = mapper.map(email, UserInfo.class);
		List<UserInfo> info = userrepo.getid(email);
		for(UserInfo user:info)
		{
			if(user.getId()>0);
			{
				return user.getId();
			}
		}
		return 0;
		
	}
	@Override
	public boolean save(Registerdto dto,String queue) {
		System.out.println(dto.getEmail());
		UserInfo userinfo = mapper.map(dto, UserInfo.class);
		userinfo.setPassword(encoder.encode(userinfo.getPassword()));
		boolean check = userrepo.save(userinfo);
		if (check) {
			reciever.messagerecieve(queue);
			return true;
		}
		return false;
	}

	@Override
	public List<UserInfo> getallusers() {

		return userrepo.getallusers();
	}

	

	@Override
	public String dologin(Logindto user) {
		UserInfo userinfo = mapper.map(user, UserInfo.class);
		System.out.println(user.getEmail());
		UserInfo info = userrepo.dologin(userinfo.getEmail());
		System.out.println(info);
		long ids = info.getId();
		String token = usertoken.tokengenerate(ids);
		System.out.println(token+"huiuu");
		System.out.println(info.getPassword());
		if (BCrypt.checkpw(user.getPassword(), info.getPassword())) {
			System.out.println("matched");
			
			return token;
		}
		return null;
	}

	@Override
	public boolean sendmail(Logindto user) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(user.getEmail());
		message.setFrom("gauravpreet.98@gmail.com");
		message.setSubject("change password");
		UserInfo userinfo = mapper.map(user, UserInfo.class);
		List<UserInfo> info = userrepo.sendemail(userinfo.getEmail());
		for(UserInfo userInfo:info)
		{	System.out.println(userInfo.getId());
			String token = usertoken.tokengenerate(userInfo.getId());
			message.setText("http://localhost:8082/user/verify?token="+token);
			mailsender.send(message);
		}
		
		LOGGER.info("mail sends");
		return true;
	}

	@Override
	public boolean verify(String token) {
		long id = usertoken.parseToken(token);
		System.out.println(id);
		userrepo.isverify(id);
		return true;
	}

	@Override
	public boolean forgetpassword(Logindto user) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(user.getEmail());
		message.setFrom("gauravpreet.98@gmail.com");
		message.setSubject("change password");
		UserInfo userinfo = mapper.map(user, UserInfo.class);
		UserInfo users = userrepo.forgetpassword(userinfo.getEmail());
		String token = usertoken.tokengenerate(users.getId());
		message.setText("http://localhost:3000/changepassword/"+users.getId());
		if (user.getEmail().equals(users.getEmail())) {
			mailsender.send(message);
			System.out.println("mail sends");
			return true;
		}
		return false;
	}

	@Override
	public boolean changepassword(long id,Logindto user) {
		String newpassword = encoder.encode(user.getPassword());
		System.out.println(newpassword);
		boolean check = userrepo.changepassword(id, newpassword);
		if (check) {
			return true;
		}
		return false;
	}

	

}
