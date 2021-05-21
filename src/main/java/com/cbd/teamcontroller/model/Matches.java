package com.cbd.teamcontroller.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.HashSet;
import java.util.Set;


import javax.persistence.Column;
import javax.persistence.ElementCollection;
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

	@Column(name = "golesLocal")
	private Integer goalsLocal; 
	
	@Column(name = "golesVisitante")
	private Integer goalsVisitor;
	
	@ElementCollection(targetClass = String.class)
	private Set<String> playersConovated = new HashSet<String>();
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Matches other = (Matches) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	} 
	
}
