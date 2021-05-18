package com.cbd.teamcontroller.model.dtos;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cbd.teamcontroller.model.StatusMatch;
import com.cbd.teamcontroller.model.Team;

public class MatchesDTO {

	
	private static SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy/MM/dd");
	private static SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm:ss");
	
	private Integer id;
	
	private String date; 
	

	private String startTime;
	

	private String endTime;

	private String callTime;
	

	private String callPlace;
	
	private StatusMatch status;  
	

	private String matchPlace; 

	private Team localTeam; 
	

	private String visitorTeam;

	
	
	public MatchesDTO(Integer id, Date date,  Date startTime,  Date endTime,
			 Date callTime,  String callPlace, StatusMatch status,  String matchPlace,
			 Team localTeam,  String visitorTeam) {
		super();
		this.id = id;
		this.date = transformarFechas(date);
		this.startTime = transformarFechasATime(startTime);
		this.endTime = transformarFechasATime(endTime);
		this.callTime = transformarFechasATime(callTime);
		this.callPlace = callPlace;
		this.status = status;
		this.matchPlace = matchPlace;
		this.localTeam = localTeam;
		this.visitorTeam = visitorTeam;
	} 
	
	
	public static String transformarFechas(Date date) {
		return formatterDate.format(date);
	}
	
	public static String transformarFechasATime(Date date) {
		return formatterTime.format(date);
	}

}
