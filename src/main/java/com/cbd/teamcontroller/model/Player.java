package com.cbd.teamcontroller.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cbd.teamcontroller.model.dtos.PlayerDTO;
import com.cbd.teamcontroller.model.mapper.UserDataMapper;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "player")
public class Player extends User {
	
	private static final long serialVersionUID = 1L;
	
	public Player(PlayerDTO playerDTO) {
		this.goalsPerMatch = playerDTO.getGoalsPerMatch();
		this.yellowsPerMatch = playerDTO.getYellowsPerMatch();
		this.redPerMatch = playerDTO.getRedPerMatch();
		this.minutesPerMatch = playerDTO.getMinutesPerMatch();
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Trainings> trainings; 
	
	@OneToMany()
	private List<Matches> matches; 
	
	@OneToOne
	private Team team; 
	
	@Column(name = "goals_match")
	private Integer goalsPerMatch; 
	
	@Column(name = "total_goals")
	private Integer totalGoals; 
	
	@Column(name = "yellows_match")
	private Integer yellowsPerMatch; 
	
	@Column(name = "total_yellows")
	private Integer totalYellows; 
	
	@Column(name = "position")
	private String position;
	
	@Column(name = "reds_match")
	private Integer redPerMatch; 
	
	@Column(name = "total_reds")
	private Integer totalReds; 
	
	@Column(name = "minutes_match")
	private Integer minutesPerMatch;
	
	@Column(name = "total_minutes")
	private Integer totalMinutes; 
	
	public Player(UserDataMapper userData) {
		super(userData.getUsername(), userData.getFirstName(), userData.getSecondName(), userData.getPhoneNumber(),userData.getPassword(), userData.getFechaNacimiento(), userData.getRol());
	}

}
