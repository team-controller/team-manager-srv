package com.cbd.teamcontroller.configuration.security.payload.response;

import com.cbd.teamcontroller.model.Team;

public class LoginResponse {
	
	private String token;
	private String type = "Bearer";
	private String username;
	private String fechaNacimiento;
	private String firstName;
	private String secondName;
	private String role;
	private Team team; 


	public LoginResponse(String token, String username, String fechaNacimiento, String firstName,
			String secondName, String role, Team team) {
		super();
		this.token = token;
		this.username = username;
		this.fechaNacimiento = fechaNacimiento;
		this.firstName = firstName;
		this.secondName = secondName;
		this.role = role;
		this.team = team;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getsecondName() {
		return secondName;
	}

	public void setsecondName(String secondName) {
		this.secondName = secondName;
	}
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}
