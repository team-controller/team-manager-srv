package com.cbd.teamcontroller.controller;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cbd.teamcontroller.model.Coach;
import com.cbd.teamcontroller.model.Matches;
import com.cbd.teamcontroller.model.Player;
import com.cbd.teamcontroller.model.StatusMatch;
import com.cbd.teamcontroller.model.Team;
import com.cbd.teamcontroller.service.CoachService;
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
	
	@Autowired
	private CoachService coachService;

	
	static class SortByDate implements Comparator<Matches> {
        @Override
        public int compare(Matches a, Matches b) {
            return a.getDate().compareTo(b.getDate());
        }
	
	}

	@GetMapping("/matches")
	@PreAuthorize("permitAll()")
	public ResponseEntity<List<Matches>> getAllMatchesByTeam() {
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ud.getUsername();

		Team t = this.teamService.findTeamByCoachUsername(username);
		if(t != null) {
			List<Matches> matches  = t.getMatches().stream().collect(Collectors.toList());
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

		if (authorities.contains("ROLE_COACH")) {
			Team t = this.teamService.findTeamByCoachUsername(username);
			match.setStatus(StatusMatch.valueOf("PENDIENTE"));
			match.setLocalTeam(t.getName());
			this.matchesService.saveMatch(match);
			t.getMatches().add(match);
			this.teamService.save(t);
			return ResponseEntity.ok(match);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/match/delete/{idMatch}")
	@PreAuthorize("hasRole('COACH')")
	public ResponseEntity<Set<Matches>> deleteMatch(@PathVariable("idMatch") Integer idMatch) {
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ud.getUsername();

		if (idMatch != null) {
			Matches m = this.matchesService.getMatchesByID(idMatch);
			Team t = this.teamService.findTeamByCoachUsername(username);
			t.getMatches().remove(m);
			this.teamService.save(t);
			this.matchesService.removeMatch(idMatch);
			return ResponseEntity.ok(t.getMatches());
		}

		return ResponseEntity.badRequest().build();
	}

//	@GetMapping("/team/{idTeam}/player/{usernamePlayer}/c")
//	@PreAuthorize("hasRole('COACH')")
//	public ResponseEntity<Matches> setPlayerInMatchList(@PathVariable("idTeam") Integer idTeam,
//			@PathVariable("usernamePlayer") String usernamePlayer) {
//		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String username = ud.getUsername();
//		Team t = teamService.findById(idTeam);
//		if (t != null) {
//			if (t.getCoach().getUsername().equals(username)) {
//				Player p = playerService.findByUsername(usernamePlayer);
//				if (p != null) {
//					ZonedDateTime serverDefaultTime = ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault());
//					ZoneId madridZone = ZoneId.of("Europe/Madrid");
//					ZonedDateTime madridZoned = serverDefaultTime.withZoneSameInstant(madridZone);
//					LocalDateTime dt = madridZoned.toLocalDateTime();
//
//					Date date = Date.from(dt.atZone(ZoneId.systemDefault()).toInstant());
//					Timestamp ts = new Timestamp(date.getTime());
//					
//					List<Matches> partidos = new ArrayList<>(t.getMatches());
//					Comparator<Matches> c = (m1, m2) -> m1.getDate().compareTo(m2.getDate());
//					Collections.sort(partidos, c);
//					
//					Optional<Matches> m = partidos.stream().filter(x -> x.getDate().after(ts)).findFirst();
//					Matches m2 = null;
//					if (m.isPresent()) {
//						m2 = m.get();
////						m2.getConvocados().add(p);
//						matchesService.saveMatch(m2);
//					}
//					if (m2 != null) {
//						return ResponseEntity.ok().build();
//					}
//				}
//			} else {
//				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//			}
//		}
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//	}
	
	@GetMapping("/team/{idTeam}/match/{idMatch}/player/{usernamePlayer}/convocar")
//	@PreAuthorize("hasRole('COACH')")
	public ResponseEntity<Matches> convocar(@PathVariable("idMatch") Integer idMatch, @PathVariable("idTeam") Integer idTeam,
			@PathVariable("usernamePlayer") String usernamePlayer) {
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ud.getUsername();
		Team t = teamService.findById(idTeam);
		if (t != null) {
			if (t.getCoach().getUsername().equals(username)) {
				Matches m = matchesService.findById(idMatch);
				Player p = playerService.findByUsername(usernamePlayer);
				if (p != null && m != null) {
					m.getPlayersConovated().add(p.getUsername());
					this.matchesService.saveMatch(m);
					return ResponseEntity.ok().build();
				}
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@GetMapping("/match/{idMatch}/player/{usernamePlayer}/desconvocar")
	@PreAuthorize("hasRole('COACH')")
	public ResponseEntity<Matches> desconvocar(@PathVariable("idMatch") Integer idMatch, @PathVariable("idTeam") Integer idTeam,
			@PathVariable("usernamePlayer") String usernamePlayer) {
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ud.getUsername();
		Coach c = this.coachService.findByUsername(username);
		if (c != null) {
				Player p = playerService.findByUsername(usernamePlayer);
				Matches m = matchesService.findById(idMatch);
				if (p != null && m != null) {
					m.getPlayersConovated().remove(p.getUsername());
					this.matchesService.saveMatch(m);
					return ResponseEntity.ok().build();
				}
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	
	@PutMapping("/match/edit/")
	@PreAuthorize("hasRole('COACH')")
	public ResponseEntity<Matches> updateMatch(@RequestBody Matches match) { 
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ud.getUsername();
		List<String> authorities = ud.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		
		if(authorities.contains("ROLE_COACH")) {
			 Team t = this.teamService.findTeamByCoachUsername(username);
			 Matches m = this.matchesService.findById(match.getId());
			 t.getMatches().remove(m);
			 this.matchesService.saveMatch(match);
			 t.getMatches().add(match);
			 this.teamService.save(t);
			 return ResponseEntity.status(HttpStatus.CREATED).build();
		}else { 
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("/match/convocate/{matchId}")
	@PreAuthorize("permitAll()")
	public ResponseEntity<List<Player>> getPlayersConvocated(){ 
		
		
		
		return ResponseEntity.ok(new ArrayList<Player>());

	}
	

}
