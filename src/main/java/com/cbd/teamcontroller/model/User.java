package com.cbd.teamcontroller.model;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cbd.teamcontroller.model.utils.RoleType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Id
	@Column(name = "username")
	private String username;
	
	@NotBlank
	protected String password;
	
	@NotBlank
	@Column(name = "first_name")
	private String firstName;
	
	@NotBlank
	@Column(name = "last_name")
	private String secondName;
	
	@NotNull
	@Pattern(regexp = "^(\\d{4})[\\/](0?[1-9]|1[012])[\\/](0?[1-9]|[12][0-9]|3[01])$")
	@Column(name = "fecha_nacimiento")
	private String fechaNacimiento; 

	@NotBlank
	@Column(name = "phone_number" ) 
	private String phoneNumber; 
	
	
	@NotNull
	@Column(name = "role")
	protected RoleType rol;

	public User() {
	}
	
	public User(String username, String firstName, String secondName, String phoneNumber, String password,
			String fechaNacimiento, RoleType rol) {
		this.username = username; 
		this.firstName = firstName; 
		this.secondName = secondName; 
		this.phoneNumber = phoneNumber; 
		this.password = password; 
		this.fechaNacimiento = fechaNacimiento; 
		this.rol = rol; 
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(rol.getAuthority());
	}
	@Override
	public String getUsername() {
		return this.username;
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}


	@Override
	public boolean isEnabled() {
		return true;
	}
}
