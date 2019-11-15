package com.springboot.fundoonotes;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.springboot.application.dto.Logindto;
import com.springboot.application.model.Login;
import com.springboot.application.model.UserInfo;
import com.springboot.application.repositry.UserImplRepo;
import com.springboot.application.service.UserImpl;




@RunWith(MockitoJUnitRunner.class)
public class LoginTest {
@InjectMocks
private UserImpl userService;

@Mock
private UserImplRepo userRepository;
@Autowired
private MockMvc mvc;
@Ignore
@Test
public void loginTest() {



List<UserInfo> listofusers=new ArrayList<UserInfo>();
Logindto user =new Logindto();
user.setEmail("gauravpreet.98@gmail.com");
user.setPassword("123456");

Login loginexpected =new Login();
loginexpected.setEmail("gauravpreet.98@gmail.com");

UserInfo userinfo=new UserInfo();
userinfo.setIsverified(true);


when(userRepository.findbyemail(user.getEmail())).thenReturn(userinfo);

 Login login=userService.dologin(user);
 
assertEquals(loginexpected.getEmail(),listofusers);
 
//        assertEquals(1, excepteddata.size());
}

@Test
public void getlogin()
{
	mvc.perform(MockMvcRequestBuilders
			.get("/login")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath(expression, matcher))
			
}

//@Ignore
//    @Test
//    public void testLoginUserSuccess() throws URISyntaxException
//    {
//        RestTemplate restTemplate = new RestTemplate();
//        final String baseUrl = "http://localhost:+8085/user/login";
//        URI uri = new URI(baseUrl);
//        Logindto user = new Logindto( "chandrakishore314@email.com","1234");
//         
////        HttpHeaders headers = new HttpHeaders();
////        headers.set("X-COM-PERSIST", "true");    
// 
//        HttpEntity<Login> request = new HttpEntity<>(user);
//        try
//        {
//        ResponseEntity<Response> response = restTemplate.exchange(uri, HttpMethod.POST,request, Response.class);
//        Assertions.assertEquals(201,response.getStatusCode());
//        }
//        catch(HttpClientErrorException ex)
//        {
//            //Verify bad request and missing header
//            Assert.assertEquals(400, ex.getRawStatusCode());
//            Assert.assertEquals(true, ex.getResponseBodyAsString().contains("Missing request header"));
//		}
//	}
}