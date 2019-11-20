package com.springboot.fundoonotes;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.application.dto.Logindto;
import com.springboot.application.model.Login;
import com.springboot.application.model.UserInfo;
import com.springboot.application.repositry.UserImplRepo;
import com.springboot.application.service.UserImpl;




@RunWith(MockitoJUnitRunner.class)
public class LoginTest {
@InjectMocks
private UserImpl userService;

@MockBean
private UserImplRepo userRepository;
@Autowired
private MockMvc mvc;

@Test

public void loginTest() {



List<UserInfo> listofusers=new ArrayList<>();
UserInfo user =new UserInfo();
user.setEmail("gauravpreet.98@gmail.com");
user.setPassword("123456");
listofusers.add(user);

UserInfo userinfo=new UserInfo();
userinfo.setIsverified(true);


//when(userRepository.findbyemail(user.getEmail())).thenReturn(listofusers);
//
// Login login=userService.dologin(user);
 
assertEquals("",listofusers);
 
//        assertEquals(1, excepteddata.size());
}

@Test
@Ignore
public void Testlogin() throws Exception
{
	mvc.perform(post("user/login")
			.content(asJsonString(new Login("gauravpreet.98@gmail.com","123456")))
			.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().is2xxSuccessful());
			
}
@Test
@Ignore
public void TestForgotPassword() throws Exception
{	
	mvc.perform(MockMvcRequestBuilders
			.post("user/forgetpassword")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
			
}
@Test
@Ignore
public void TestgetAllUser() throws Exception
{	
	mvc.perform(MockMvcRequestBuilders
			.get("user/")
			.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
			
}
public static String asJsonString(final Login obj) {
    try {
        return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
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