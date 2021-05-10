package com.cbd.teamcontroller.model.mapper;

import java.util.Date;
import java.util.Set;

import com.cbd.teamcontroller.model.utils.RoleType;

import lombok.Data;

@Data
public class UserDataMapper {

	private String username;
	private String	firstName;
	private String	secondName;
	private String	phoneNumber;
	private String	password;
	private Date fechaNacimiento; 
	private RoleType rol;
	
	public UserDataMapper() {
	}

	public UserDataMapper(String username, String firstName, String lastName, String phoneNumber, String password, Date fechaNacimiento,  RoleType roles) {
		this.username = username;
		this.firstName = firstName;
		this.secondName = lastName;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.fechaNacimiento = fechaNacimiento;
		this.rol = roles;
	}
}
