package com.springboot.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.springboot.application.dto.Labeldto;
import com.springboot.application.exceptions.Response;
import com.springboot.application.model.Label;
import com.springboot.application.service.LabelService;

@RestController
@RequestMapping("/label")
public class LabelController {
	
	@Autowired
	private LabelService labelservice;
	@PostMapping(value = "/createlabel")
	public ResponseEntity<Response> createlabel(@RequestHeader String token,@RequestHeader long id,@RequestBody Labeldto label) {
		boolean check = labelservice.createlabel(token,id, label);
		if (check) {
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response,HttpStatus.OK);
		} else {
			Response response1 = new Response("not successful", HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/deletelabel/{id}")
	public ResponseEntity<Response> deletelabel(@PathVariable(value = "id") long id, @RequestHeader String token) {
		System.out.println("inside controller");
		boolean check = labelservice.deletelabel(token, id);
		
		if (check) {
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			Response response1 = new Response("not successful", HttpStatus.BAD_GATEWAY.value());
			return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping(value = "/updatelabel/{id}")
	public ResponseEntity<Response> updatelabel(@PathVariable(value = "id") long id, @RequestHeader String token,
			@RequestBody Labeldto label) {
		System.out.println("inside controller");
		boolean check = labelservice.updatelabel(id, token, label);
		if (check) {
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response,  HttpStatus.OK);
		} else {
			Response response1 = new Response("not successful", HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);
		}

	}
	@GetMapping(value = "/getlabel")
	public ResponseEntity<Response> getlabel( @RequestHeader String token) {
		System.out.println("inside controller");
		List<Label>labels = labelservice.getlabels(token);
		if (labels.size()>0) {
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response,  HttpStatus.OK);
		} else {
			Response response1 = new Response("not successful", HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);
		}

	}
}
