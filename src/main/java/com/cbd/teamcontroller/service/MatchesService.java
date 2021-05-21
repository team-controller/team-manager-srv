package com.cbd.teamcontroller.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cbd.teamcontroller.model.Matches;
import com.cbd.teamcontroller.repository.MatchesRepository;

@Service
public class MatchesService {

		
		@Autowired
		private MatchesRepository matchesRepository;
		
		
		public Matches getMatchesByID(Integer integerId) { 
			 Optional<Matches> matches = this.matchesRepository.findById(integerId);
			 if(matches.isPresent()) {
				 return matches.get();
			 }else { 
				 return null;
			 }
		}
		
		public List<Matches> getAllMatches() {
			return this.matchesRepository.findAll();
		}
		
		public void saveMatch(Matches match) { 
			this.matchesRepository.save(match);
		}
	    public void removeMatch(Integer id) {
	    	this.matchesRepository.deleteById(id);
	    }
	    
	    public Matches findById(Integer idMatch) { 
	    	Optional<Matches> m =  this.matchesRepository.findById(idMatch);
	    	if(m.isPresent()) { 
	    		return m.get();
	    	}else { 
	    		return null;
	    	}
	    }
}
