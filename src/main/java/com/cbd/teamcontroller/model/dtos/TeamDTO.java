package com.cbd.teamcontroller.model.dtos;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamDTO {
	
	@NotBlank
	private String name; 
	
	@NotBlank
	private String city;
	
	@NotBlank
	private String stadiumName;
	
}
