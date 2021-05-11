package com.cbd.teamcontroller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbd.teamcontroller.model.Coach;
import com.cbd.teamcontroller.repository.CoachRepository;

@Service
public class CoachService {

	@Autowired
	private CoachRepository coachRepository;

	public Coach findByUsername(String username) {
		return coachRepository.findByUsername(username);
	}
	
	public Coach save(Coach c) {
		return coachRepository.save(c);
	}
}
