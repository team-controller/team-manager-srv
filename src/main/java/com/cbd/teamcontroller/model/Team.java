package com.cbd.teamcontroller.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cbd.teamcontroller.model.dtos.TeamDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "team")
public class Team extends BaseEntity{

	public Team () { 
		
	}
	
	public Team(@Valid TeamDTO teamDTO) {
		this.name = teamDTO.getName();
		this.stadiumName = teamDTO.getStadiumName();
		this.city = teamDTO.getCity();
	}

	@NotBlank
	@Column(name = "name")
	private String name; 
	
	@NotBlank
	@Column(name = "stadiumNname")
	private String stadiumName; 
	
	@NotBlank
	@Column(name = "city")
	private String city; 
	
	@Column(name = "points")
	private Integer points; 
	
	@JsonIgnore
	@OneToOne()
	private Coach coach;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	Set<Player> players; 
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	Set<Matches> matches; 
	
	
}
