package com.cbd.teamcontroller.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.cbd.teamcontroller.model.Player;
import com.cbd.teamcontroller.model.Team;
import com.cbd.teamcontroller.model.dtos.PlayerDTO;
import com.cbd.teamcontroller.model.mapper.UserDataMapper;
import com.cbd.teamcontroller.service.PlayerService;
import com.cbd.teamcontroller.service.TeamService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class PlayerController {

	@Autowired
	private TeamService teamService;

	@Autowired
	private PlayerService playerService;

	@GetMapping("/team/{idTeam}/players")
	@PreAuthorize("permitAll()")
	public ResponseEntity<Set<Player>> getPlayersTeam(@PathVariable("idTeam") Integer idTeam) {
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ud.getUsername();
		Team t = teamService.findById(idTeam);
		if (t != null) {
			List<String> namesPlayers = t.getPlayers().stream().map(x -> x.getUsername()).collect(Collectors.toList());
			if (username.equals(t.getCoach().getUsername()) || namesPlayers.contains(username)) {
				return ResponseEntity.ok(t.getPlayers());
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/team/{idTeam}/player/{usernamePlayer}")
	@PreAuthorize("permitAll()")
	public ResponseEntity<Player> getPlayer(@PathVariable("idTeam") Integer idTeam,
			@PathVariable("usernamePlayer") String usernamePlayer) {
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ud.getUsername();
		Team t = teamService.findById(idTeam);
		if (t != null) {
			List<String> namesPlayers = t.getPlayers().stream().map(x -> x.getUsername()).collect(Collectors.toList());
			if (username.equals(t.getCoach().getUsername()) || namesPlayers.contains(username)) {
				Player p = playerService.findByUsername(usernamePlayer);
				if (p != null) {
					String date = p.getFechaNacimiento();
					String[] element = date.split(" ");
					String finalDate = element[0].replace("-", "/");
					p.setFechaNacimiento(finalDate);

					return ResponseEntity.ok(p);
				}
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/team/{idTeam}/player/{position}/create")
	@PreAuthorize("hasRole('COACH')")
	public ResponseEntity<Player> createPlayer(@PathVariable("idTeam") Integer idTeam, @RequestBody UserDataMapper user,
			@PathVariable("position") String posicion) {
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ud.getUsername();
		Team t = teamService.findById(idTeam);
		if (t != null) {
			if (t.getCoach().getUsername().equals(username)) {
				Player p = new Player(user);
				p.setTeam(t);
				p.setPosition(posicion);
				playerService.save(p);
				t.getPlayers().add(p);
				teamService.save(t);
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/team/{idTeam}/player/{usernamePlayer}/edit/{position}")
	@PreAuthorize("hasRole('COACH')")
	public ResponseEntity<Player> editInfoPersonalPlayer(@PathVariable("idTeam") Integer idTeam,
			@PathVariable("usernamePlayer") String usernamePlayer, @PathVariable("position") String posicion,
			@RequestBody UserDataMapper res) {
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ud.getUsername();
		Team t = teamService.findById(idTeam);
		if (t != null) {
			if (t.getCoach().getUsername().equals(username)) {
				Player p = playerService.findByUsername(usernamePlayer);
				if (p != null) {
					String date = res.getFechaNacimiento();
					String[] element = date.split(" ");
					String finalDate = element[0].replace("-", "/");
					res.setFechaNacimiento(finalDate);
					Player p1 = p;
					p = new Player(res);
					p.setMatches(p1.getMatches());
					p.setTotalGoals(p1.getTotalGoals());
					p.setTotalMinutes(p1.getTotalMinutes());
					p.setTotalYellows(p1.getTotalYellows());
					p.setTotalReds(p1.getTotalReds());
					p.setPosition(posicion);
					playerService.save(p);
					return ResponseEntity.ok().build();
				}
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/team/{idTeam}/player/{usernamePlayer}/editMatch")
	@PreAuthorize("hasRole('COACH')")
	public ResponseEntity<Player> editInfoPartidosPlayer(@PathVariable("idTeam") Integer idTeam,
			@PathVariable("usernamePlayer") String usernamePlayer, @RequestBody PlayerDTO playerDTO) {
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ud.getUsername();
		Team t = teamService.findById(idTeam);
		if (t != null) {
			if (t.getCoach().getUsername().equals(username)) {
				Player p = playerService.findByUsername(usernamePlayer);
				if (p != null) {
					p.setTotalGoals(p.getTotalGoals() + playerDTO.getGoalsPerMatch());
					p.setTotalYellows(p.getTotalYellows() + playerDTO.getYellowsPerMatch());
					p.setTotalReds(p.getTotalReds() + playerDTO.getRedPerMatch());
					p.setTotalMinutes(p.getTotalMinutes() + playerDTO.getMinutesPerMatch());

					String date = p.getFechaNacimiento();
					String[] element = date.split(" ");
					String finalDate = element[0].replace("-", "/");
					p.setFechaNacimiento(finalDate);

					playerService.save(p);
					return ResponseEntity.ok().build();
				}
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/team/{idTeam}/player/{usernamePlayer}/delete")
	@PreAuthorize("hasRole('COACH')")
	public ResponseEntity<Player> deletePlayer(@PathVariable("idTeam") Integer idTeam,
			@PathVariable("usernamePlayer") String usernamePlayer) {
		UserDetails ud = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ud.getUsername();
		Team t = teamService.findById(idTeam);
		if (t != null) {
			if (t.getCoach().getUsername().equals(username)) {
				Player p = playerService.findByUsername(usernamePlayer);
				if (p != null) {
					t.getPlayers().remove(p);
					teamService.save(t);
					playerService.remove(p);
					return ResponseEntity.ok().build();
				}
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
		}
		return ResponseEntity.notFound().build();
	}

}
