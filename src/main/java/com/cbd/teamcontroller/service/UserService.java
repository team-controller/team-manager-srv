package com.cbd.teamcontroller.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cbd.teamcontroller.repository.UserRepository;
import com.cbd.teamcontroller.model.User;

@Service
public class UserService implements UserDetailsService{
    
	private UserRepository userRepository;
	
    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
		
	}
    public void saveUser(User user) {
        this.userRepository.save(user);
    }
    
    public User findUserByUserName(String username) {
    	Optional<User> userOpt = this.userRepository.findByUsername(username);
    	if(userOpt.isPresent()) {
    		return userOpt.get();
    	}else { 
    		return null;
    	}
    }
	
}
