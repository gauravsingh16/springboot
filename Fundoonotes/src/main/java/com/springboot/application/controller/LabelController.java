package com.springboot.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.application.dto.Labeldto;
import com.springboot.application.model.Response;
import com.springboot.application.service.NoteService;

@RestController
@RequestMapping("/label")
public class LabelController {
	
	@Autowired
	private NoteService noteservice;
	@PostMapping(value = "/createlabel")
	public ResponseEntity<Response> createlabel(@RequestHeader String token,@RequestHeader long id,@RequestBody Labeldto label) {
		boolean check = noteservice.createlabel(token,id, label);
		HttpHeaders header = new HttpHeaders();
		if (check) {
			header.add("note creation", "successfully done");
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response, header, HttpStatus.OK);
		} else {
			header.add("note creation", "unsuccessful");
			Response response1 = new Response("not successful", HttpStatus.BAD_GATEWAY.value());
			return new ResponseEntity<>(response1, header, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/deletelabel/{id}")
	public ResponseEntity<Response> deletelabel(@PathVariable(value = "id") long id, @RequestHeader String token) {
		System.out.println("inside controller");
		boolean check = noteservice.deletelabel(token, id);
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

	@PutMapping(value = "/updatelabel/{id}")
	public ResponseEntity<Response> updatelabel(@PathVariable(value = "id") long id, @RequestHeader String token,
			@RequestBody Labeldto label) {
		System.out.println("inside controller");
		boolean check = noteservice.updatelabel(id, token, label);
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
}
