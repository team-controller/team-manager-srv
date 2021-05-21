package com.cbd.teamcontroller.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "matches")
public class Matches extends BaseEntity{
	
	@NotNull
	@Column(name = "date")
	@JsonFormat(pattern="yyyy/MM/dd")
	private Date date; 
	
	@NotNull
	@Column(name = "start_time")
	@JsonFormat(pattern="HH:mm:ss")
	private Date startTime;
		
	@NotNull
	@Column(name = "call_time")
	@JsonFormat(pattern="HH:mm:ss")
	private Date callTime;
	
	@NotBlank
	@Column(name = "callPlace")
	private String callPlace;
	
	@Column(name = "status")
	private StatusMatch status;  
	
	@NotBlank
	@Column(name = "matchPlace")
	private String matchPlace; 
	
	@Column(name = "localTeam")
	private String localTeam; 
	
	@NotBlank
	@Column(name = "visitorTeam")
	private String visitorTeam; 
	
	
}
