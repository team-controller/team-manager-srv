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
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "team")
public class Team extends BaseEntity{

	@NotBlank
	@Column(name = "name")
	private String name; 
	
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
