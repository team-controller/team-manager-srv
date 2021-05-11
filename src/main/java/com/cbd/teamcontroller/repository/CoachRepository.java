package com.cbd.teamcontroller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cbd.teamcontroller.model.Coach;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Integer> {

	@Query("select c from coach c where c.username = :username")
	Coach findByUsername(String username);

}
