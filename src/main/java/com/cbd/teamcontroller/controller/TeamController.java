package com.cbd.teamcontroller.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
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
import com.cbd.teamcontroller.model.Player;
import com.cbd.teamcontroller.model.Team;
import com.cbd.teamcontroller.model.dtos.TeamDTO;
import com.cbd.teamcontroller.service.CoachService;
import com.cbd.teamcontroller.service.PlayerService;
import com.cbd.teamcontroller.service.TeamService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TeamController {

	@Autowired
	private CoachService coachService;

	@Autowired
	private TeamService teamService;

	@Autowired
	private PlayerService playerService;

	@GetMapping("/team/")
	@PreAuthorize("permitAll()")
	public ResponseEntity<Map<Integer,Object>> getTeamByPlayer() {
		Map<Integer,Object> res = new HashMap<>();
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ud.getUsername();
		List<String> authorities = ud.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		
		if(authorities.contains("ROLE_COACH")) {
			Team t = teamService.findTeamByCoachUsername(username);
			if (t != null) {
				res.put(0, t);
				return new ResponseEntity<>(res,HttpStatus.OK);
			}
		}else { 
			Player p = playerService.findByUsername(username);
			if (p != null) {
				List<Team> teams = teamService.findAll();
				if (!teams.isEmpty()) {
					Optional<Team> t = teams.stream().filter(x -> x.getPlayers().contains(p)).findFirst();
					Team teamRes = null;
					if (t.isPresent()) {
						teamRes = t.get();
						res.put(0,teamRes);
					}
					return ResponseEntity.ok(res);
				}
			}
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
			
			String date = c.getFechaNacimiento();
			String[] element = date.split(" ");
			String finalDate = element[0].replace("-", "/");
			c.setFechaNacimiento(finalDate);
			c.setTeam(t);
			coachService.save(c);

			return ResponseEntity.ok(t);
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
				edit.setPoints(t.getPoints());
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
				return ResponseEntity.ok().build();
			}
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	@GetMapping("/haveTeam/")
	@PreAuthorize("hasRole('COACH')")
	public ResponseEntity<Boolean> haveTeam() { 
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ud.getUsername();
		List<String> authorities = ud.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		if(authorities.contains("ROLE_COACH")) {
			Team t = this.teamService.findTeamByCoachUsername(username);
			if (t != null) {
				return ResponseEntity.ok(true);
			}else {
				return ResponseEntity.ok(false);
			}
		}
		return ResponseEntity.badRequest().build();
	}

}
