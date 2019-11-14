package com.springboot.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.application.dto.Notedto;
import com.springboot.application.exceptions.Response;
import com.springboot.application.model.Note;
import com.springboot.application.model.UserInfo;
import com.springboot.application.service.NoteService;
@RestController
@RequestMapping("/note")
@CrossOrigin(origins="*",exposedHeaders= {"jwt_token"})
public class NoteController {
	
	@Autowired
	private NoteService noteservice;

	
	@PostMapping(value="/createnote")
	public ResponseEntity<Response> createnote(@RequestHeader String token,@RequestBody Notedto note) {
		System.out.println(note.getTitle());
		System.out.println(token);
		boolean check = noteservice.createnote(token,note);
		if (check) {
			
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			Response response1 = new Response("not successful", HttpStatus.BAD_GATEWAY.value());
			return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);
		}
	}
	@DeleteMapping(value="/deletenote")
	public ResponseEntity<Response>deletenote(@RequestParam long id,@RequestHeader String token)
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
	@PutMapping(value="/updatenote")
	public ResponseEntity<Response>updatenote(@RequestParam long id, @RequestHeader String token,@RequestBody Notedto note)
	{	System.out.println("inside controller");
			System.out.println(note);
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
	@GetMapping("/search")
	public ResponseEntity<Response> searchnote(@RequestHeader String token, @RequestParam String title)
	{		
		System.out.println(title);
		System.out.println("inside controller");
		List<Note>notes=noteservice.searchnote(token,title);
		if (notes.size()>0) {
			Response response = new Response("successfull", HttpStatus.OK.value(),notes);
			return new ResponseEntity<>(response,  HttpStatus.OK);

		} else {
			Response response1 = new Response("not successful", HttpStatus.BAD_GATEWAY.value());
			return new ResponseEntity<>(response1,  HttpStatus.BAD_REQUEST);		}
	
		}
	
	

	@PutMapping(value="/archivednotes")
	public ResponseEntity<Response> updatearchive(@RequestParam long id, @RequestHeader String token)
	{	System.out.println("insidde");
		boolean check=noteservice.archivednotes(id,token);
		if(check)
		{
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response,  HttpStatus.OK);
		}else
		{
			Response response1 = new Response("not successful", HttpStatus.BAD_GATEWAY.value());
			return new ResponseEntity<>(response1,  HttpStatus.BAD_REQUEST);		}
	}
	@GetMapping(value="/getarchivednotes")
	public ResponseEntity<Response> getarchive(@RequestHeader String token)
	{
		List<Note>note=noteservice.getarchivenote(token);
		if(note.size()>0)
		{

			Response response = new Response("successfull", HttpStatus.OK.value(),note);
			return new ResponseEntity<>(response,  HttpStatus.OK);
		
		}else
		{

			Response response1 = new Response("not successful", HttpStatus.BAD_GATEWAY.value());
			return new ResponseEntity<>(response1,  HttpStatus.BAD_REQUEST);		}
			
		}
	
	
	@PostMapping(value="/pinnotes")
	public ResponseEntity<Response> updatepin(@RequestParam long id, @RequestHeader String token)
	{	System.out.println("insidde");
		boolean check=noteservice.updatepin(id,token);
		if(check)
		{
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response,  HttpStatus.OK);
		}else
		{
			Response response1 = new Response("not successful", HttpStatus.BAD_GATEWAY.value());
			return new ResponseEntity<>(response1,  HttpStatus.BAD_REQUEST);		}
	}
	@GetMapping(value="/getpinnotes")
	public ResponseEntity<Response> getpin(@RequestHeader String token)
	{
		List<Note>note=noteservice.getpinnote(token);
		if(note.size()>0)
		{
			Response response = new Response("successfull", HttpStatus.OK.value(),note);
			return new ResponseEntity<>(response,  HttpStatus.OK);
		}else
		{
			Response response1 = new Response("not successful", HttpStatus.BAD_GATEWAY.value());
			return new ResponseEntity<>(response1,  HttpStatus.BAD_REQUEST);		
	
		}
	}
	@PutMapping(value="/trashnotes")
	public ResponseEntity<Response> updatetrash(@RequestParam Long id, @RequestHeader String token)
	{	System.out.println("insidde");
		boolean check=noteservice.updatetrash(id,token);
		if(check)
		{
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response,  HttpStatus.OK);
		}else
		{
			Response response1 = new Response("not successful", HttpStatus.BAD_GATEWAY.value());
			return new ResponseEntity<>(response1,  HttpStatus.BAD_REQUEST);		}
	}
	@GetMapping(value="/gettrashnotes")
	public ResponseEntity<Response> gettrashnote(@RequestHeader String token)
	{
		List<Note>note=noteservice.gettrashnote(token);
		if(note.size()>0)
		{
			Response response = new Response("successfull", HttpStatus.OK.value(),note);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}else {
			Response response1 = new Response("not successful", HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);

		}
	
	}
	@GetMapping(value ="/getnotes")
	public ResponseEntity<Response> getnotes( @RequestHeader String token) {
		System.out.println("inside controller");
		System.out.println(token);
		List<Note>notes = noteservice.getnotes(token);
		if (notes.size()>0) {
			System.out.println(notes);
			Response response = new Response("successfull", HttpStatus.OK.value(),notes);
			return new ResponseEntity<>(response, HttpStatus.OK);

		} else {
			Response response1 = new Response("not successful", HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);

		}
	}
	@PostMapping(value ="/doreminder")
	public ResponseEntity<Response> doreminder(@RequestParam long id,@RequestBody Notedto dto ,@RequestHeader String token) {
		System.out.println("inside controller");
		System.out.println(token);
		boolean notes = noteservice.doreminder(id,dto,token);
		if (notes) {
			System.out.println(notes);
			Response response = new Response("successfull", HttpStatus.OK.value(),notes);
			return new ResponseEntity<>(response, HttpStatus.OK);

		} else {
			Response response1 = new Response("not successful", HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);

		}
	}
	@DeleteMapping(value ="/removereminder")
	public ResponseEntity<Response> removereminder(@RequestParam long id,@RequestHeader String token) {
		System.out.println("inside controller");
		System.out.println(token);
		boolean notes = noteservice.removereminder(id,token);
		if (notes) {
			System.out.println(notes);
			Response response = new Response("successfull", HttpStatus.OK.value(),notes);
			return new ResponseEntity<>(response, HttpStatus.OK);

		} else {
			Response response1 = new Response("not successful", HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);

		}
	}
	@GetMapping(value ="/getremindernotes")
	public ResponseEntity<Response> remindernotes(@RequestHeader String token) {
		System.out.println("inside controller");
		System.out.println(token);
		List<Note> notes = noteservice.remindernotes(token);
		if (notes.size()>0) {
			System.out.println(notes);
			Response response = new Response("successfull", HttpStatus.OK.value(),notes);
			return new ResponseEntity<>(response, HttpStatus.OK);

		} else {
			Response response1 = new Response("not successful", HttpStatus.OK.value());
			return new ResponseEntity<>(response1, HttpStatus.OK);

		}
	}
	@PutMapping(value ="/changecolor")
	public ResponseEntity<Response> changecolor(@RequestParam long id,@RequestBody Notedto dto,@RequestHeader String token) {
		System.out.println("inside controller");
		System.out.println(token);
		boolean notes = noteservice.changecolor(id,dto,token);
		if (notes) {
			System.out.println(notes);
			Response response = new Response("successfull", HttpStatus.OK.value(),notes);
			return new ResponseEntity<>(response, HttpStatus.OK);

		} else {
			Response response1 = new Response("not successful", HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);

		}
	}
	@PostMapping(value ="/docollaborator")
	public ResponseEntity<Response> docollaborator(@RequestParam("id") long id,@RequestParam("email") String email,@RequestHeader String token) {
		System.out.println("inside controller");
		System.out.println(token);
		boolean notes = noteservice.doCollab(id,email,token);
		if (notes) {
			System.out.println(notes);
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response, HttpStatus.OK);

		} else {
			Response response1 = new Response("not successful", HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);

		}
	}	
	@GetMapping(value ="/getcollaborator")
	public ResponseEntity<Response> getcollaborator(@RequestParam long id,@RequestHeader String token) {
		System.out.println("inside controller");
		System.out.println(token);
		List<UserInfo> notes = noteservice.getCollab(id,token);
		if (notes.size()>0) {
			System.out.println(notes);
			Response response = new Response("successfull", HttpStatus.OK.value(),notes);
			return new ResponseEntity<>(response, HttpStatus.OK);

		} else {
			Response response1 = new Response("not successful", HttpStatus.OK.value(),notes);
			return new ResponseEntity<>(response1, HttpStatus.OK);

		}
	}
	@DeleteMapping(value ="/deletecollaborator")
	public ResponseEntity<Response> deletecollaborator(@RequestParam long noteid,@RequestParam long userid,@RequestHeader String token) {
		System.out.println("inside controller");
		System.out.println(token);
		boolean notes = noteservice.deleteCollab(noteid,userid,token);
		if (notes) {
			System.out.println(notes);
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response, HttpStatus.OK);

		} else {
			Response response1 = new Response("not successful", HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);

		}
	}
		
	
	
	
}
