package com.cbd.teamcontroller.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "matches")
public class Matches extends BaseEntity{
	
	@NotBlank
	@Column(name = "date")
	private Date date; 
	
	@NotBlank
	@Column(name = "start_time")
	private LocalDateTime startTime;
	
	@NotBlank
	@Column(name = "end_time")
	private LocalDateTime endTime;
	
	@NotBlank
	@Column(name = "call_time")
	private LocalDateTime callTime;
	
	@NotBlank
	@Column(name = "callPlace")
	private String callPlace;
	
	@Column(name = "status")
	private StatusMatch status;  
	
	@NotBlank
	@Column(name = "matchPlace")
	private String matchPlace; 
	
	@OneToOne
	private Team localTeam; 
	
	@OneToOne
	private Team visitorTeam; 
	
	
}
