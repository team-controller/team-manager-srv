package com.cbd.teamcontroller.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "trainings")
public class Trainings extends BaseEntity{
	
	@NotBlank
	@Column(name = "start_date")
	private Date startDate; 
	
	@NotBlank
	@Column(name = "end_date")
	private Date endDate;
	
	@NotBlank
	@Column(name = "start_time")
	private LocalDateTime startTime;
	
	@NotBlank
	@Column(name = "end_time")
	private LocalDateTime endTime;
	
	
	
	
}
