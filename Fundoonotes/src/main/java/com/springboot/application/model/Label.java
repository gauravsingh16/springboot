package com.springboot.application.model;

import java.util.ArrayList;
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

@Entity
@Table(name = "label")
public class Label {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "labelid")
	private long labelId;
	@Column(name = "name")
	private String name;
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name="label_note",joinColumns = {@JoinColumn(name="labelid")},inverseJoinColumns= {
			@JoinColumn(name="note_id")})
	private List<Note> noteid;
	
	public void addnote(Note note)
	{
		if(noteid == null)
		{
			noteid=new ArrayList<Note>();
		}
		noteid.add(note);
	}
	public long getLabelId() {
		return labelId;
	}
	public void setLabelId(long labelId) {
		this.labelId = labelId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
