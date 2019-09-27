package com.springboot.application.config;

import java.util.Date;

import org.springframework.context.annotation.Configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
@Configuration
public class UserToken {
	private static final long EXPIRATION_TIME=3600000;
	private static final String secret_key="123456789";
	public String tokengenerate(Long userid) 
	{

		String token=null;
		try {
			token=JWT.create().withClaim("id", userid).withExpiresAt(new Date(System.currentTimeMillis()+EXPIRATION_TIME)).sign(Algorithm.HMAC256(secret_key));
			System.out.println(userid);
		}catch(IllegalArgumentException e)
		{
			e.printStackTrace();
		}catch(JWTCreationException e)
		{
			e.printStackTrace();
		}
		return token;
	}
	
	public long parseToken(String token)
	{
	long userid=0;
	if(token!=null)
	{
		userid=JWT.require(Algorithm.HMAC256(secret_key)).build().verify(token).getClaim("id").asInt();
	}
	return userid;
	}
}
