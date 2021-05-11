package com.cbd.teamcontroller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cbd.teamcontroller.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer>{

}
