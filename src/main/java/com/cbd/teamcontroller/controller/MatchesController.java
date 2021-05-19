package com.cbd.teamcontroller.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cbd.teamcontroller.model.Matches;
import com.cbd.teamcontroller.model.Player;
import com.cbd.teamcontroller.model.StatusMatch;
import com.cbd.teamcontroller.model.Team;
import com.cbd.teamcontroller.service.MatchesService;
import com.cbd.teamcontroller.service.PlayerService;
import com.cbd.teamcontroller.service.TeamService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class MatchesController {
	
	@Autowired
	private MatchesService matchesService;
	
	@Autowired
	private TeamService teamService; 
	
	@Autowired
	private PlayerService playerService;
	
	
	
	@GetMapping("/matches")
	@PreAuthorize("permitAll()")
	public ResponseEntity<List<Matches>> getAllMatchesByTeam() {
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ud.getUsername();
        
		Team t = this.teamService.findTeamByCoachUsername(username);
		if(t != null) {
			List<Matches> matches  = t.getMatches().stream().collect(Collectors.toList());
//			Set<MatchesDTO> matches  = new HashSet<>();
//			for (Matches m : t.getMatches()) {
//				MatchesDTO mDTO = new MatchesDTO(m.getId(),m.getDate(), m.getStartTime(), m.getEndTime(), m.getCallTime(), m.getCallPlace(),m.getStatus(), 
//						m.getMatchPlace(), m.getLocalTeam(), m.getVisitorTeam());
//				matches.add(mDTO);
//			}
//			System.out.print(matches);
			return new ResponseEntity<>(matches, HttpStatus.OK);
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@GetMapping("/oneMatch/{matchId}")
	@PreAuthorize("permitAll()")
	public ResponseEntity<Matches> getOneMatchDetails(@PathVariable("matchId") Integer matchId) { 
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ud.getUsername();
		List<String> authorities = ud.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		if (authorities.contains("ROLE_COACH")) {
			Team t = this.teamService.findTeamByCoachUsername(username);
			Matches match = t.getMatches().stream().filter(x -> x.getId().equals(matchId)).findFirst().get();
			if (match != null) {
				return ResponseEntity.ok(match);
			} else {
				return ResponseEntity.notFound().build();
			}
		} else if (authorities.contains("ROLE_PLAYER")) {
			Player p = this.playerService.findByUsername(username);
			List<Team> teams = teamService.findAll();
			if (!teams.isEmpty()) {
				Team t = teams.stream().filter(x -> x.getPlayers().contains(p)).findFirst().get();
				Matches match = t.getMatches().stream().filter(x -> x.getId().equals(matchId)).findFirst().get();
				return ResponseEntity.ok(match);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	
	@PostMapping("/match/create/")
	@PreAuthorize("hasRole('COACH')")
	public ResponseEntity<Matches> createMatch(@Valid @RequestBody Matches match) { 
		
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ud.getUsername();
		List<String> authorities = ud.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		
		if(authorities.contains("ROLE_COACH")) {
			 Team t = this.teamService.findTeamByCoachUsername(username);
			 match.setStatus(StatusMatch.valueOf("PENDIENTE"));
			 match.setLocalTeam(t.getName());
			 this.matchesService.saveMatch(match);
			 t.getMatches().add(match);
			 this.teamService.save(t);
			 return ResponseEntity.ok(match);
		}else { 
			return ResponseEntity.badRequest().build();
		}
	}
	
	
	@DeleteMapping("/match/delete/{idMatch}")
	@PreAuthorize("hasRole('COACH')")
	public ResponseEntity<Set<Matches>> deleteMatch(@PathVariable("idMatch") Integer idMatch) {
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ud.getUsername();
		
		if(idMatch != null) { 
			Matches m = this.matchesService.getMatchesByID(idMatch);
			Team t = this.teamService.findTeamByCoachUsername(username);
			t.getMatches().remove(m);
			this.teamService.save(t);
			this.matchesService.removeMatch(idMatch);
			return ResponseEntity.ok(t.getMatches());
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	
	
	
	
	
	
	
}
