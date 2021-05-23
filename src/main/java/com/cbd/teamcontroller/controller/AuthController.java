package com.cbd.teamcontroller.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cbd.teamcontroller.configuration.security.jwt_configuration.JwtUtils;
import com.cbd.teamcontroller.configuration.security.payload.request.LoginRequest;
import com.cbd.teamcontroller.configuration.security.payload.request.SignupRequest;
import com.cbd.teamcontroller.configuration.security.payload.response.LoginResponse;
import com.cbd.teamcontroller.model.Coach;
import com.cbd.teamcontroller.model.Player;
import com.cbd.teamcontroller.model.Team;
import com.cbd.teamcontroller.model.User;
import com.cbd.teamcontroller.model.mapper.UserDataMapper;
import com.cbd.teamcontroller.model.utils.RoleType;
import com.cbd.teamcontroller.service.PlayerService;
import com.cbd.teamcontroller.service.TeamService;
import com.cbd.teamcontroller.service.UserService;

@CrossOrigin(origins="*", maxAge=3600, methods= {RequestMethod.PATCH,  RequestMethod.POST})
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
	@Autowired
    PasswordEncoder encoder;
	@Autowired
	UserService userService; 
	@Autowired
	AuthenticationManager authenticationManager; 
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	PlayerService playerService;
	
	
	@PostMapping("/signin")
	public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		var authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		var userDetails = (User) authentication.getPrincipal();
		List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		Team team = null;
		if(authorities.contains("ROLE_COACH")) {
			team = teamService.findTeamByCoachUsername(userDetails.getUsername());	
		}else {
			Player p = this.playerService.findByUsername(userDetails.getUsername());
			team = p.getTeam();
		}
		return ResponseEntity.ok(new LoginResponse(jwt, userDetails.getUsername(), userDetails.getFechaNacimiento(), userDetails.getFirstName(), userDetails.getSecondName(), userDetails.getRol().getName(),team));
	
	}
	
	@PostMapping("/signup")
	public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signup) { 
		UserDataMapper userData = new UserDataMapper(signup.getUsername(), signup.getFirstName(), 
				signup.getSecondName(), signup.getPhoneNumber(), encoder.encode(signup.getPassword()), 
				signup.getFechaNacimiento(), (RoleType.valueOf(signup.getRole())));

		User userWithRole = generateUserWithRole(userData);
		
		this.userService.saveUser(userWithRole);
		
		return ResponseEntity.ok(new MessageResponse("El usuario ha quedado registrado correctamente"));
	}
	
    public User generateUserWithRole(UserDataMapper userData) {
        
    	if (userData.getRol().equals(RoleType.ROLE_COACH)) {
            return new Coach(userData);
        } else if (userData.getRol().equals(RoleType.ROLE_PLAYER)) {
            return new Player(userData);
        }
        return null;
    }
	
	
}
