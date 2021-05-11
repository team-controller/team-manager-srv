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

import com.cbd.teamcontroller.model.dtos.TeamDTO;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "team")
public class Team extends BaseEntity{

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
	
	@NotBlank
	@Column(name = "points")
	private Integer points; 
	
	@OneToOne()
	private Coach coach;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	Set<Player> players; 
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	Set<Matches> matches; 
	
	
}
