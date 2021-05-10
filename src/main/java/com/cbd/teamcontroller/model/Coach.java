package com.cbd.teamcontroller.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.cbd.teamcontroller.model.mapper.UserDataMapper;
import com.cbd.teamcontroller.model.utils.RoleType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "coach")
public class Coach extends User{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne
	private Team team;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Trainings> trainings; 
	
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Matches> matches;
	
	
    public Coach() {
    }

	public Coach(String username, String firstName, String secondName, String phoneNumber, String password,
			Date fechaNacimiento, RoleType rol, Team team) {
		super(username, firstName, secondName, phoneNumber, password, fechaNacimiento, rol);
		this.team = team;
	}
    
	
    public Coach(UserDataMapper userData) {
        super(userData.getUsername(), userData.getFirstName(), userData.getSecondName(), 
        		userData.getPhoneNumber(), userData.getPassword(),userData.getFechaNacimiento(), userData.getRol());
    }





}
