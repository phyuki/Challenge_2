package br.com.compass.challenge2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.compass.challenge2.entity.Squad;
import br.com.compass.challenge2.repository.SquadRepository;

@Service
public class SquadService implements CrudService<Squad> {

	private SquadRepository squadRepository;

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
		return squadRepository.findById(id).orElse(null);
	}

	@Override
	public Squad save(Squad squad) {
		return squadRepository.save(squad);
	}

	@Override
	public Squad update(Squad squad) {
		if (squadRepository.existsById(squad.getId()))
			return squadRepository.save(squad);

		throw new IllegalArgumentException();
	}

	public Squad update(Long id, Squad uptSquad) {
		Squad currentSquad = squadRepository.findById(id).orElse(null);
		if (currentSquad != null) {
			currentSquad.setSquadName(uptSquad.getSquadName());
			currentSquad.setStudents(uptSquad.getStudents());
			return squadRepository.save(currentSquad);

		}
		return null;
	}

	@Override
	public void delete(Squad squad) {
		/*
		 * Squad currentSquad = squadRepository.findById(id).orElse(null);
		 * if (currentSquad != null) {
		 * squadRepository.delete(currentSquad);
		 * return true;
		 * }else
		 * return false;
		 */
		squadRepository.delete(squad);
	}

	@Override
	public void deleteById(Long id) {
		squadRepository.deleteById(id);
	}

}
