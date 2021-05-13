package com.cbd.teamcontroller.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbd.teamcontroller.model.Team;
import com.cbd.teamcontroller.repository.TeamRepository;

@Service
public class TeamService {

	@Autowired
	private TeamRepository teamRepository;

	public void save(Team t) {
		this.teamRepository.save(t);
	}

	public Team findById(Integer idTeam) {
		Optional<Team> team = this.teamRepository.findById(idTeam);
		Team res = null;
		if (team.isPresent()) {
			res = team.get();
		}
		return res;
	}

	public Team findTeamByCoachUsername(String username) {
		return this.teamRepository.findTeamByCoachUsername(username);
	}
	
	
	public void delete(Team t) {
		this.teamRepository.delete(t);
	}
	
}
