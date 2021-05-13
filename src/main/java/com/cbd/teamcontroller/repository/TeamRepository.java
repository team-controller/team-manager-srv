package com.cbd.teamcontroller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cbd.teamcontroller.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer>{

	@Query("select t from Team t where t.coach.username = :username")
	Team findTeamByCoachUsername(String username);

}
