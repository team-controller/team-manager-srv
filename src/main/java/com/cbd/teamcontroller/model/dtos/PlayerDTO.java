package com.cbd.teamcontroller.model.dtos;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDTO {
	
	@NotNull
	private Integer goalsPerMatch; 
	
	@NotNull
	private Integer yellowsPerMatch; 
	
	@NotNull
	private Integer redPerMatch; 
	
	@NotNull
	private Integer minutesPerMatch;
	
}
