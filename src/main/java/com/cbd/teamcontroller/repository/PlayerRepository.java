package com.cbd.teamcontroller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cbd.teamcontroller.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer>{

	@Query("select p from player p where p.username = :username")
	Player findByUsername(String username);

}
