package com.cbd.teamcontroller.controller;

import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cbd.teamcontroller.model.Coach;
import com.cbd.teamcontroller.model.Team;
import com.cbd.teamcontroller.model.dtos.TeamDTO;
import com.cbd.teamcontroller.service.CoachService;
import com.cbd.teamcontroller.service.TeamService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TeamController {

	@Autowired
	private CoachService coachService;

	@Autowired
	private TeamService teamService;

	@GetMapping("/team/")
	@PreAuthorize("permitAll()")
	public ResponseEntity<Team> getTeam() {
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ud.getUsername();
		Coach c = coachService.findByUsername(username);
		Team t = c.getTeam();
		if (t != null) {
			return ResponseEntity.ok(t);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/createTeam")
	@PreAuthorize("hasRole('COACH')")
	public ResponseEntity<Team> createTeam(@Valid @RequestBody TeamDTO teamDTO) {
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ud.getUsername();
		Coach c = coachService.findByUsername(username);
		if (c != null) {
			Team t = new Team(teamDTO);
			t.setCoach(c);
			t.setMatches(new HashSet<>());
			t.setPlayers(new HashSet<>());
			t.setPoints(0);
			teamService.save(t);

			c.setTeam(t);
			coachService.save(c);

			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@PutMapping("/team/edit")
	@PreAuthorize("hasRole('COACH')")
	public ResponseEntity<Team> editTeam(@Valid @RequestBody TeamDTO teamDTO) {
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ud.getUsername();
		Coach c = coachService.findByUsername(username);
		if (c != null) {
			Team t = c.getTeam();
			if (t != null) {
				Team edit = new Team(teamDTO);
				edit.setId(t.getId());
				edit.setCoach(c);
				edit.setMatches(t.getMatches());
				edit.setPlayers(t.getPlayers());
				teamService.save(edit);

				return ResponseEntity.status(HttpStatus.CREATED).build();
			}
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@DeleteMapping("/team/delete")
	@PreAuthorize("hasRole('COACH')")
	public ResponseEntity<Team> deleteTeam() {
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ud.getUsername();
		Coach c = coachService.findByUsername(username);
		if (c != null) {
			Team t = c.getTeam();
			if (t != null) {
				c.setTeam(null);
				coachService.save(c);
				teamService.delete(t);
			}
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

}
