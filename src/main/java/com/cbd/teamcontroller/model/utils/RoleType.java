package com.cbd.teamcontroller.model.utils;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum RoleType {
	ROLE_PLAYER(new SimpleGrantedAuthority("ROLE_PLAYER")),
	ROLE_COACH(new SimpleGrantedAuthority("ROLE_COACH"));
	
	private final String name;
	private final SimpleGrantedAuthority authority;
	
	RoleType(SimpleGrantedAuthority authority) {
		this.authority = authority;
		this.name = authority.getAuthority();
	}

	public SimpleGrantedAuthority getAuthority() {
		return authority;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
