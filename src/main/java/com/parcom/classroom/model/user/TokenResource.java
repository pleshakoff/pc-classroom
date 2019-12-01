package com.parcom.classroom.model.user;

public class TokenResource
{

	private final String token;
	private final Long id;

	public TokenResource(String token, Long id)
	{
		this.token = token;
		this.id = id;
	}

	public String getToken()
	{
		return this.token;
	}

	public Long getId() {
		return id;
	}
}