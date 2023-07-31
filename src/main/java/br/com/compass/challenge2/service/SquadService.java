package br.com.compass.challenge2.service;

import br.com.compass.challenge2.entity.Squad;
import br.com.compass.challenge2.repository.SquadRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SquadService implements CrudService<Squad> {

	private final SquadRepository squadRepository;

	@Autowired
	public SquadService(SquadRepository squadRepository) {
		this.squadRepository = squadRepository;
	}

	@Override
	public List<Squad> findAll() {
		return squadRepository.findAll();
	}

	@Override
	public Squad findById(Long id) {
		return squadRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Squad does not exist with id:" + id));
	}

	@Override
	public Squad save(Squad squad) {
		return squadRepository.save(squad);
	}
	
	public Squad update(Squad squad) {
		if (squadRepository.existsById(squad.getId())) {
			return squadRepository.save(squad);
		}
		throw new IllegalArgumentException("Squad with Id:" + squad.getId() + "not found.");
	}

	@Override
	public Squad deleteById(Long id) {
        Squad squad;
        try {
            squad = squadRepository.findById(id).get();
            squadRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Squad does not exist with id: " + id);
        }
        return squad;
    
	}

	public Squad update(Long id, Squad updatedSquad) {
		Squad currentSquad = squadRepository.findById(id).orElse(null);
		if (currentSquad != null) {
			currentSquad.setSquadName(updatedSquad.getSquadName());
			currentSquad.setStudents(updatedSquad.getStudents());
			return squadRepository.save(currentSquad);
		}
		return null;
	}
	
	// Filtro de pesquisa (Achar pelo nome da Squad)
	 public List<Squad> findBySquadNameContainingIgnoreCase(String squadName) {
	        return squadRepository.findBySquadNameContainingIgnoreCase(squadName);
	    }	
}
