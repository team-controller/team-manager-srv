package com.cbd.teamcontroller.configuration.security.payload.request;

import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.cbd.teamcontroller.model.mapper.UserDataMapper;
import com.cbd.teamcontroller.model.utils.RoleType;

@Getter
@Setter
public class SignupRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;


    private String role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    
	@NotNull
	@Pattern(regexp = "^(\\d{4})[\\/](0?[1-9]|1[012])[\\/](0?[1-9]|[12][0-9]|3[01])$")
	private String fechaNacimiento; 

    @NotBlank
    private String firstName;

    @NotBlank
    private String secondName;

    @Pattern(regexp = "^[+]*[(]?[0-9]{1,4}[)]?[-\\s\\./0-9]*$", message = "Must be a valid phone number")
    private String phoneNumber;
    
    
    public SignupRequest() { 
    	
    }
    public SignupRequest(UserDataMapper user) { 
    	this.username = user.getUsername();
    	this.password = user.getPassword();
    	this.role = user.getRol().getName();
    	this.fechaNacimiento = user.getFechaNacimiento();
    	this.firstName = user.getFirstName();
    	this.secondName = user.getSecondName();
    	this.phoneNumber = user.getPhoneNumber();
    }

}