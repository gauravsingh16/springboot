package com.springboot.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.springboot.application.service.NoteService;
@RestController
@RequestMapping("/note")
@CrossOrigin(origins="http://localhost:4200",exposedHeaders= {"jwt_token"})
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
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response,  HttpStatus.OK);

		} else {
			Response response1 = new Response("not successful", HttpStatus.BAD_GATEWAY.value());
			return new ResponseEntity<>(response1,  HttpStatus.BAD_REQUEST);		}
	
		}
	
	

	@PostMapping(value="/archivednotes")
	public ResponseEntity<Response> updatearchive(@RequestParam long id, @RequestHeader String token)
	{	System.out.println("insidde");
		boolean check=noteservice.getarchivednotes(id,token);
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
		List<Note>note=noteservice.getarchivenote();
		if(note.size()>0)
		{

			Response response = new Response("successfull", HttpStatus.OK.value());
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
		List<Note>note=noteservice.getpinnote();
		if(note.size()>0)
		{
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response,  HttpStatus.OK);
		}else
		{
			Response response1 = new Response("not successful", HttpStatus.BAD_GATEWAY.value());
			return new ResponseEntity<>(response1,  HttpStatus.BAD_REQUEST);		
	
		}
	}
	@PostMapping(value="/trashnotes")
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
		List<Note>note=noteservice.gettrashnote();
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
	public ResponseEntity<Response> getlabel( @RequestHeader String token) {
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
	
	
	
	
}
