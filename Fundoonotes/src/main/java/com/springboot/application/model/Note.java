package com.springboot.application.model;

import java.time.LocalDateTime;
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
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
@Entity
@Table(name="note_details")
public class Note {
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	@Column(name="noteId")
	private long noteId;
	@Column(name="title")
	private String title;
	@Column(name="description")
	private String desc;
	@Column(name="color")
	private String color;
	@Column(name="updatedtime")
	private LocalDateTime updatetime=LocalDateTime.now();
	@Column(name="createdtime")
	private LocalDateTime createtime=LocalDateTime.now();
	@Column(name="pin")
	private boolean pin;
	@Column(name="archive")
	private boolean archive;
	@Column(name="trash")
	private boolean trash;
	@Column(name="reminder")
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime reminder;
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name="label_note",joinColumns = {@JoinColumn(name="note_id")},inverseJoinColumns= {
			@JoinColumn(name="labelid")})
	private List<Label> label;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="user_note",joinColumns = {@JoinColumn(name="note_id")},inverseJoinColumns= {
			@JoinColumn(name="userId")})
	 @JsonIgnore
	private List<UserInfo> user;
	
public List<UserInfo> getUser() {
		return user;
	}
	public void setUser(List<UserInfo> user) {
		this.user = user;
	}
public LocalDateTime getReminder() {
		return reminder;
	}
	public void setReminder(LocalDateTime reminder) {
		this.reminder = reminder;
	}
	//	public void addnote(Note note)
//	{
//		if(labelid == null)
//		{
//			labelid=new ArrayList<Note>();
//		}
//		labelid.add(note);
//	}
	public boolean isTrash() {
		return trash;
	}
	public void setTrash(boolean trash) {
		this.trash = trash;
	}
	
	
	
	public List<Label> getLabel() {
		return label;
	}
	public void setLabel(List<Label> label) {
		this.label = label;
	}
	public long getNoteId() {
		return noteId;
	}
	public void setNoteId(long noteId) {
		this.noteId = noteId;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public long getId() {
		return noteId;
	}
	public void setId(long noteId) {
		this.noteId = noteId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public LocalDateTime getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(LocalDateTime updatetime) {
		this.updatetime = updatetime;
	}
	public LocalDateTime getCreatetime() {
		return createtime;
	}
	public void setCreatetime(LocalDateTime createtime) {
		this.createtime = createtime;
	}
	public boolean isPin() {
		return pin;
	}
	public void setPin(boolean pin) {
		this.pin = pin;
	}
	public boolean isArchive() {
		return archive;
	}
	public void setArchive(boolean archive) {
		this.archive = archive;
	}
	
}
