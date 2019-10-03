package com.springboot.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.application.dto.Notedto;
import com.springboot.application.exceptions.Response;
import com.springboot.application.model.Note;
import com.springboot.application.service.NoteService;
@RestController
@RequestMapping("/note")
public class NoteController {
	
	@Autowired
	private NoteService noteservice;

	
	@PostMapping(value="/createnote")
	public ResponseEntity<Response> createnote(@RequestHeader String token,@RequestBody Notedto note) {
		boolean check = noteservice.createnote(token,note);
		if (check) {
			
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			Response response1 = new Response("not successful", HttpStatus.BAD_GATEWAY.value());
			return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);
		}
	}
	@DeleteMapping(value="/deletenote/{id}")
	public ResponseEntity<Response>deletenote(@PathVariable(value="id") long id,@RequestHeader String token)
	{	System.out.println("inside controller");
		boolean check=noteservice.deletenote(token, id);
		HttpHeaders header = new HttpHeaders();
		if (check) {
			header.add("note deletion", "successfully done");
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response, header, HttpStatus.OK);
		} else {
			header.add("note deletion", "unsuccessful");
			Response response1 = new Response("not successful", HttpStatus.BAD_GATEWAY.value());
			return new ResponseEntity<>(response1, header, HttpStatus.BAD_REQUEST);
		}
	
	}
	@PutMapping(value="/updatenote/{id}")
	public ResponseEntity<Response>updatenote(@PathVariable(value="id") long id,@RequestHeader String token,@RequestBody Notedto note)
	{	System.out.println("inside controller");
		boolean check=noteservice.updatenote(id,token,note);
		HttpHeaders header = new HttpHeaders();
		if (check) {
			header.add("note update", "successfully done");
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response, header, HttpStatus.OK);
		} else {
			header.add("note update", "unsuccessful");
			Response response1 = new Response("not successful", HttpStatus.BAD_GATEWAY.value());
			return new ResponseEntity<>(response1, header, HttpStatus.BAD_REQUEST);
		}
	
	}
	@GetMapping(value="/search")
	public List<Note>searchnote(@RequestHeader String title)
	{	System.out.println("inside controller");
		List<Note>notes=noteservice.searchnote(title);
		if (notes.size()>0) {
			return notes;
		
		} else {
			return null;
			}
	
	}
	@GetMapping(value = "/getnotes")
	public ResponseEntity<Response> getlabel( @RequestHeader String token) {
		System.out.println("inside controller");
		List<Note>notes = noteservice.getnotes(token);
		if (notes.size()>0) {
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response,  HttpStatus.OK);
		} else {
			Response response1 = new Response("not successful", HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);
		}

	}
}
