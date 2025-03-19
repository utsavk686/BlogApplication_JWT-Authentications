package com.wipro.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
public class JWTResponse {
	private String token;
	private String username;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public JWTResponse(String token, String username) {
		super();
		this.token = token;
		this.username = username;
	}
	
	
	public JWTResponse() {
		
	}

}