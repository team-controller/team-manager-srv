package com.cbd.teamcontroller.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbd.teamcontroller.model.Player;
import com.cbd.teamcontroller.repository.PlayerRepository;

@Service
public class PlayerService {
	
	@Autowired
	private PlayerRepository playerRepository;

	public Player findById(Integer idPlayer) {
		Optional<Player> p = this.playerRepository.findById(idPlayer);
		Player res = null;
		if(p.isPresent()) {
			res = p.get();
		}
		return res;
	}

	public void save(Player p) {
		this.playerRepository.save(p);
	}

	public Player findByUsername(String username) {
		return this.playerRepository.findByUsername(username);
	}

	public void remove(Player p) {
		this.playerRepository.delete(p);
	}

}
