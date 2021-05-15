package com.cbd.teamcontroller.configuration.security.payload.response;


public class LoginResponse {
	
	private String token;
	private String type = "Bearer";
	private String username;
	private String fechaNacimiento;
	private String firstName;
	private String secondName;
	private String role;
	private Boolean hasTeam; 


	public LoginResponse(String token, String username, String fechaNacimiento, String firstName,
			String secondName, String role, Boolean hasTeam) {
		super();
		this.token = token;
		this.username = username;
		this.fechaNacimiento = fechaNacimiento;
		this.firstName = firstName;
		this.secondName = secondName;
		this.role = role;
		this.hasTeam = hasTeam;
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
	public Boolean getHasTeam() {
		return hasTeam;
	}

	public void setHasTeam(Boolean hasTeam) {
		this.hasTeam = hasTeam;
	}

}
