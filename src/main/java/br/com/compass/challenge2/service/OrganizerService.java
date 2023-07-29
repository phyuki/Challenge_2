package br.com.compass.challenge2.service;

import br.com.compass.challenge2.entity.Organizer;
import br.com.compass.challenge2.repository.OrganizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizerService implements CrudService<Organizer> {

    private final OrganizerRepository organizerRepository;

    @Autowired
    public OrganizerService(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    @Override
    public Organizer findById(Long id) {
        return organizerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Organizer> findAll() {
        return organizerRepository.findAll();
    }

    @Override
    public Organizer save(Organizer organizer) {
        return organizerRepository.save(organizer);
    }
    @Override
    public Organizer update(Organizer organizer) {
        return organizerRepository.save(organizer);
    }

    @Override
    public void delete(Organizer organizer) {
        organizerRepository.delete(organizer);
    }

    @Override
    public void deleteById(Long id) {
        organizerRepository.deleteById(id);
    }
}
