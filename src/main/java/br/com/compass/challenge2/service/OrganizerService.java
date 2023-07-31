package br.com.compass.challenge2.service;

import br.com.compass.challenge2.entity.Organizer;
import br.com.compass.challenge2.entity.Role;
import br.com.compass.challenge2.repository.OrganizerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrganizerService implements CrudService<Organizer> {

    private final OrganizerRepository organizerRepository;

    @Autowired
    public OrganizerService(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    @Override
    public List<Organizer> findAll() {
        return organizerRepository.findAll();
    }

    @Override
    public Organizer findById(Long id) {
        return organizerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Organizer not found with id: " + id));
    }

    @Override
    public Organizer save(Organizer organizer) {
        return organizerRepository.save(organizer);
    }

    @Override
    public Organizer update(Organizer updatedOrganizer) {
        Organizer existingOrganizer = findById(updatedOrganizer.getId());
        existingOrganizer.setName(updatedOrganizer.getName());
        existingOrganizer.setEmail(updatedOrganizer.getEmail());
        existingOrganizer.setGroups(updatedOrganizer.getGroups());
        existingOrganizer.setRoles(updatedOrganizer.getRoles());

        return organizerRepository.save(existingOrganizer);
    }

    
	@Override
	public Organizer deleteById(Long id) {
		Organizer organizer;
		try {
			organizer = organizerRepository.findById(id).get();
			organizerRepository.deleteById(id);
		} catch (NoSuchElementException e) {
			throw new EntityNotFoundException("Organizer does not exist with id: " + id);
		}

		return organizer;
	}
}
