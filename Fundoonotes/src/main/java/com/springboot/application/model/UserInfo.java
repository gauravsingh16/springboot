
package com.springboot.application.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Component
@Entity
@Table(name = "UserInfo_table")
public class UserInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userId")
	private long userId;
	@Column(name = "username")
	private String username;
	@Column(name = "name")
	private String name;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@Column(name = "address")
	private String address;

	@Column(name = "mobileno")
	private String mobileno;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private List<Note> notes;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="user_note",joinColumns = {@JoinColumn(name="userId")},inverseJoinColumns= {
			@JoinColumn(name="note_id")})
	@JsonIgnore
	private List<Note> note;
	


	public List<Note> getNote() {
		return note;
	}

	public void setNote(List<Note> note) {
		this.note = note;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@OneToMany
	@JoinColumn(name ="userId" )
	public List<Label> getManynotes;
	

	public List<Label> getGetManynotes() {
		return getManynotes;
	}

	public void setGetManynotes(List<Label> getManynotes) {
		this.getManynotes = getManynotes;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	private boolean isverified;

	public boolean isIsverified() {
		return isverified;
	}

	public void setIsverified(boolean isverified) {
		this.isverified = isverified;
	}

	public long getId() {
		return userId;
	}

	public void setId(long userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserInfo(long userId, String username, String name, String email, String password, String address,
			String mobileno) {
		super();
		this.userId = userId;
		this.username = username;
		this.name = name;
		this.email = email;
		this.password = password;
		this.address = address;
		this.mobileno = mobileno;
	}
public UserInfo() {}
}
