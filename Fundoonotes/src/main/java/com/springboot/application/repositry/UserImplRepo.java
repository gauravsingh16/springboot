package com.springboot.application.repositry;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springboot.application.model.Note;
import com.springboot.application.model.UserInfo;

@Repository
public class UserImplRepo implements UserRepo {
	@Autowired
	private EntityManager entity;

	@Override
	@Transactional
	public boolean save(UserInfo info) {
		Session session = entity.unwrap(Session.class);
		session.save(info);
		return true;
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	@Transactional
	public List<UserInfo> getallusers() {
		Session currentsession = entity.unwrap(Session.class);
		List<UserInfo> users;
		users = currentsession.createQuery("from UserInfo").list();
		System.out.println(users);
		return users;
	}

	@SuppressWarnings("deprecation")
	@Transactional
	@Override
	public UserInfo getid(String email) {
		Session currentsession = entity.unwrap(Session.class);
		Query query1 = currentsession.createQuery("from UserInfo where email=:email");
		query1.setParameter("email", email);
		return (UserInfo) query1.uniqueResult();
	}

	
	@Override
	public UserInfo dologin(String email) {

		Session currentsession = entity.unwrap(Session.class);
		Query query1 = currentsession.createQuery("from UserInfo where email=:email");
		query1.setParameter("email", email);
		System.out.println(query1.list().toString());
		return (UserInfo) query1.uniqueResult();

	}

	@Transactional
	@Override
	public UserInfo sendemail(String email) {
		Session currentsession = entity.unwrap(Session.class);
		Query query1 = currentsession.createQuery("from UserInfo where email=:email");
		query1.setParameter("email", email);
		System.out.println(query1.list().toString());
		return (UserInfo) query1.uniqueResult();

	}

	@Transactional
	@Override
	public void isverify(long id) {
		Session currentsession = entity.unwrap(Session.class);
		System.out.println(id);
		Query query1 = currentsession.createQuery("Update UserInfo set isverified=true where id=:id");
		query1.setParameter("id", id);
		System.out.println(query1);
		query1.executeUpdate();
	}
	@Transactional
	@Override
	public UserInfo forgetpassword(String email) {
	
		Session currentsession = entity.unwrap(Session.class);
		System.out.println(email);
		Query query1 = currentsession.createQuery("from UserInfo where email=:email");
		query1.setParameter("email",email);
		return (UserInfo) query1.uniqueResult() ;
	}

	@SuppressWarnings("deprecation")
	@Transactional
	@Override
	public boolean changepassword(long id,String password) {
		Session currentsession = entity.unwrap(Session.class);
		System.out.println(id);
		System.out.println(password);
		Query query1= currentsession.createQuery("Update UserInfo set password=:password where id=:id");
		query1.setParameter("id",id);
		query1.setParameter("password", password);
		query1.executeUpdate();
		return true;
	}
	
	@Transactional
	@Override
	public UserInfo findbyId(long id) {
		Session currentsession = entity.unwrap(Session.class);
		Query query1 = currentsession.createQuery("from UserInfo where id=:id");
		query1.setParameter("id",id);
		return (UserInfo) query1.uniqueResult() ;
	}
	

	
}
