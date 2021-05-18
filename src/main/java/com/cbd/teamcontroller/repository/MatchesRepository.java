package com.cbd.teamcontroller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cbd.teamcontroller.model.Matches;

@Repository
public interface MatchesRepository extends JpaRepository<Matches, Integer> {
	
	
}
