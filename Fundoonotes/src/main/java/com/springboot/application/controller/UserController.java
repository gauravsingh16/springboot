package com.springboot.application.controller;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.application.dto.Logindto;
import com.springboot.application.dto.Rabbituserdto;
import com.springboot.application.dto.Registerdto;
import com.springboot.application.exceptions.Response;
import com.springboot.application.model.RabbitDetails;
import com.springboot.application.model.UserInfo;
import com.springboot.application.service.MessageSender;
import com.springboot.application.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

//	private static final Logger log = LoggerFactory.getLogger(MessageService.class);

	@Autowired
	private UserService userservice;
	@Autowired
	private RabbitTemplate rabbittemplate;
	@Autowired
	private RabbitDetails rabbitdetails;
	@Autowired
	private MessageSender messagesender;

	@GetMapping("/")
	public List<UserInfo> getalluser() {
		List<UserInfo> list = userservice.getallusers();
		return list;
	}

	@RequestMapping(path = "/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> adduser(@RequestBody Registerdto user) {
		String exchange1 = rabbitdetails.getApp1Exchange();
		String routingKey1 = rabbitdetails.getApp1RoutingKey();
		String exchange2 = rabbitdetails.getApp2Exchange();
		String routingKey2 = rabbitdetails.getApp2RoutingKey();
		 
		boolean check = false;
		if (user.getEmail().contains("gmail.com")) {
			System.out.println("exchange1");
			messagesender.sendMessage(rabbittemplate, exchange1, routingKey1, user.getEmail());
			check=userservice.save(user);
		} else {
			System.out.println("exchange2");
			messagesender.sendMessage(rabbittemplate, exchange2, routingKey2, user.getEmail());
			check=userservice.save(user);
		}
		if (check) {
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			Response response = new Response("successful", HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
		}
	}

	@PostMapping(value = "/sendemail")
	public ResponseEntity<Response> sendemail(@RequestBody Logindto user) {
		boolean check = userservice.sendmail(user);
		HttpHeaders header = new HttpHeaders();
		if (check) {
			header.add("mail sending", "successful");
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response, header, HttpStatus.OK);
		} else {
			header.add("mail sending", "unsuccessful");
			Response response = new Response("successful", HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<>(response, header, HttpStatus.BAD_GATEWAY);
		}
	}

	@GetMapping(value = "/searchuser")
	public long getuserbyemail(@RequestParam String email) {
		long id = userservice.getid(email);
		System.out.println(email);
		return id;

	}

	@GetMapping(value = "/login")
	public String dologin(@RequestBody Logindto user) {
		System.out.println("12131321");
		System.out.println(user.getEmail());
		String check = userservice.dologin(user);
		// long ids=userservice.getid(user.getEmail());
		HttpHeaders header = new HttpHeaders();
		if (!check.isEmpty()) {
			header.add("login", "successfully done");
			Response response = new Response("successfull", HttpStatus.OK.value());
			return check;
		} else {
			header.add("register", "unsuccessful");
			Response response1 = new Response("not successful", HttpStatus.BAD_GATEWAY.value());
			return null;
		}
	}

	@GetMapping(value = "/verify")
	public ResponseEntity<Response> doverify(@RequestParam String token) {
		System.out.println("inside verify");
		boolean check = userservice.verify(token);
		HttpHeaders header = new HttpHeaders();
		if (check) {
			header.add("login", "successfully done");
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response, header, HttpStatus.OK);
		} else {
			header.add("register", "unsuccessful");
			Response response1 = new Response("not successful", HttpStatus.BAD_GATEWAY.value());
			return new ResponseEntity<>(response1, header, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/forgetpassword")
	public ResponseEntity<Response> forgetpassword(@RequestBody Logindto user) {
		System.out.println(user.getEmail());
		boolean check = userservice.forgetpassword(user);
		HttpHeaders header = new HttpHeaders();
		if (check) {
			header.add("mail sending", "successful");
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response, header, HttpStatus.OK);
		} else {
			header.add("mail sending", "unsuccessful");
			Response response = new Response("successful", HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<>(response, header, HttpStatus.BAD_GATEWAY);
		}
	}

	@PutMapping(value = "/changepassword")
	public ResponseEntity<Response> changepassword(@RequestParam String token, String password) {
		boolean check = userservice.changepassword(token, password);
		HttpHeaders header = new HttpHeaders();
		if (check) {
			header.add("login", "successfully done");
			Response response = new Response("successfull", HttpStatus.OK.value());
			return new ResponseEntity<>(response, header, HttpStatus.OK);
		} else {
			header.add("register", "unsuccessful");
			Response response1 = new Response("not successful", HttpStatus.BAD_GATEWAY.value());
			return new ResponseEntity<>(response1, header, HttpStatus.BAD_REQUEST);
		}
	}
	

}