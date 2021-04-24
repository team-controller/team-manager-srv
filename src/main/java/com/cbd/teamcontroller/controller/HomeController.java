package com.cbd.teamcontroller.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "location")
@RestController
@Controller
public class HomeController {
	
	@GetMapping("/")
	public ResponseEntity<?> Home() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
