package br.com.compass.challenge2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.compass.challenge2.entity.Squad;
import br.com.compass.challenge2.repository.SquadRepository;

@Service
public class SquadService {
	
	@Autowired
	 private final SquadRepository squadRepository;

	    public SquadService(SquadRepository squadRepository) {
	        this.squadRepository = squadRepository;
	    }
	    
	 public List<Squad> getAllSquads(){
		 return squadRepository.findAll();		
	 }
	    
	 public Squad getSquadById(Long id) {
		 return squadRepository.findById(id).orElse(null);
	 }
	 
	 public Squad createSquad(Squad squad) {
		 return squadRepository.save(squad);
	 }
	 
	 public Squad updateSquad(Long id, Squad uptSquad) {
		 Squad currentSquad = squadRepository.findById(id).orElse(null);
		 if (currentSquad != null) {
			 currentSquad.setSquadName(uptSquad.getSquadName());
			 currentSquad.setStudents(uptSquad.getStudents());
			 return squadRepository.save(currentSquad);
			 
		 }
		 return null;
	 }
	 
	 public boolean deleteSquad(Long id) {
		 Squad currentSquad = squadRepository.findById(id).orElse(null);
		 if (currentSquad != null) {
			 squadRepository.delete(currentSquad);
			 return true;
		 }else
		 return false;
	 }
	   
	    
	 
	    
	

}
