package com.springboot.application.controller;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@CrossOrigin(origins="http://localhost:4200",exposedHeaders= {"jwt_token"})
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
		String queue1=rabbitdetails.getApp1Queue();
		String queue2=rabbitdetails.getApp2Queue();
		 
		boolean check = false;
		if (user.getEmail().contains("gmail.com")) {
			System.out.println("exchange1");
			messagesender.sendMessage(rabbittemplate, exchange1, routingKey1, user.getEmail());
			check=userservice.save(user,queue1);
			
		} else {
			System.out.println("exchange2");
			messagesender.sendMessage(rabbittemplate, exchange2, routingKey2, user.getEmail());
			check=userservice.save(user,queue2);
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

	@PostMapping(value = "/login")
	public ResponseEntity<Response> dologin(@RequestBody Logindto user) {
		System.out.println("12131321");
		System.out.println(user.getEmail());
		String check = userservice.dologin(user);
		// long ids=userservice.getid(user.getEmail());
		if (!(check==null)) {
			System.out.println("hello");
			Response response = new Response("successful", HttpStatus.OK.value(),check);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			Response response1 = new Response("unsuccessful", HttpStatus.BAD_GATEWAY.value());
			return new ResponseEntity<>(response1, HttpStatus.BAD_GATEWAY);
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

	@PutMapping(value = "/changepassword/{id}")
	public ResponseEntity<Response> changepassword(@PathVariable long id,@RequestBody Logindto user) {
		boolean check = userservice.changepassword(id,user);
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