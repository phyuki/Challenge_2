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
    public Organizer update(Organizer entity) {
        return null;
    }

    @Override
    public Organizer save(Organizer organizer) {
        return organizerRepository.save(organizer);
    }

    public Organizer update(Long id, Organizer updatedOrganizer) {
        Organizer existingOrganizer = findById(id);
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

    // Métodos de pesquisa e filtragem

    public List<Organizer> findByNameContainingIgnoreCase(String name) {
        return organizerRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Organizer> findByEmailContainingIgnoreCase(String email) {
        return organizerRepository.findByEmailContainingIgnoreCase(email);
    }

    public List<Organizer> findByGroupName(String groupName) {
        return organizerRepository.findByGroupName(groupName);
    }

    public List<Organizer> findByRole(Role role) {
        return organizerRepository.findByRole(role);
    }

    public List<Organizer> findAllByOrderByNameAsc() {
        return organizerRepository.findAllByOrderByNameAsc();
    }

    public List<Organizer> findAllByOrderByEmailAsc() {
        return organizerRepository.findAllByOrderByEmailAsc();
    }

    public List<Organizer> findAllByOrderByIdAsc() {
        return organizerRepository.findAllByOrderByIdAsc();
    }
}
