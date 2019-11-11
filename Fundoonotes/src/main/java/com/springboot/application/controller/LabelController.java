package com.springboot.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.springboot.application.dto.Labeldto;
import com.springboot.application.exceptions.Response;
import com.springboot.application.model.Label;
import com.springboot.application.model.Note;
import com.springboot.application.service.LabelService;

@RestController
@RequestMapping("/label")
@CrossOrigin(origins="*",exposedHeaders= {"jwt_token"})
public class LabelController {
	
	@Autowired
	private LabelService labelservice;
	@PostMapping(value = "/createlabel")
	public ResponseEntity<Response> createlabel(@RequestHeader String token,@RequestParam long id,@RequestBody Labeldto label) {
		boolean check = labelservice.createlabel(token,id, label);
		if (check) {
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response,HttpStatus.OK);
		} else {
			Response response1 = new Response("not successful", HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/deletelabel")
	public ResponseEntity<Response> deletelabel(@RequestParam("labelid") long id, @RequestHeader String token) {
		System.out.println("inside controller");
		System.out.println(id);
		boolean check = labelservice.deletelabel(token, id);
		
		if (check) {
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			Response response1 = new Response("not successful", HttpStatus.BAD_GATEWAY.value());
			return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping(value = "/updatelabel")
	public ResponseEntity<Response> updatelabel(@RequestParam long id, @RequestHeader String token,
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
		System.out.println(token);
		List<Label>labels = labelservice.getlabels(token);
		if (labels.size()>0) {
			System.out.println(labels);
			Response response = new Response("successfull", HttpStatus.OK.value(),labels);
			return new ResponseEntity<>(response,  HttpStatus.OK);	

		} else {
			Response response1 = new Response("not successful", HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);

		}

	}
	@PostMapping(value= "/labelcreate")
	public ResponseEntity<Response> addlabel(@RequestHeader String token,@RequestBody Labeldto dto){
		System.out.println("creation");
		System.out.println(dto);
		boolean check=labelservice.labelcreation(token,dto);
				if(check)
				{
					Response response = new Response("successfull", HttpStatus.OK.value());
					return new ResponseEntity<>(response,  HttpStatus.OK);	
				}else
				{
					Response response1 = new Response("not successful", HttpStatus.BAD_REQUEST.value());
					return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);
				}
	}
	@PostMapping(value="/addnotelabel")
	public ResponseEntity<Response> addnotelabel(@RequestParam long labelid,@RequestParam long noteid,@RequestHeader String token){
		System.out.println("creation"+labelid);
		System.out.println(noteid);
		boolean check=labelservice.addnotelabel(labelid,noteid,token);
				if(check)
				{
					Response response = new Response("successfull", HttpStatus.OK.value());
					return new ResponseEntity<>(response,  HttpStatus.OK);	
				}else
				{
					Response response1 = new Response("not successful", HttpStatus.BAD_REQUEST.value());
					return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);
				}
	}
	@PostMapping(value="/removenotelabel")
	public ResponseEntity<Response> removenotelabel(@RequestParam long labelid,@RequestParam long noteid,@RequestHeader String token){
		System.out.println("creation"+labelid);
		System.out.println(noteid);
		boolean check=labelservice.removenotelabel(labelid,noteid,token);
				if(check)
				{
					Response response = new Response("successfull", HttpStatus.OK.value());
					return new ResponseEntity<>(response,  HttpStatus.OK);	
				}else
				{
					Response response1 = new Response("not successful", HttpStatus.BAD_REQUEST.value());
					return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);
				}
	}
	@GetMapping(value="/getnotes")
	public ResponseEntity<Response> getnotes(@RequestParam long labelid ,@RequestHeader String token){
		System.out.println("creation"+labelid);
		List<Label> notes=labelservice.getnote(labelid,token);
				if(notes != null)
				{	System.out.println(notes);
					Response response = new Response("successfull", HttpStatus.OK.value(),notes);
					return new ResponseEntity<>(response,  HttpStatus.OK);	
				}else
				{
					Response response1 = new Response("not successful", HttpStatus.BAD_REQUEST.value());
					return new ResponseEntity<>(response1, HttpStatus.BAD_REQUEST);
				}
	}
}
