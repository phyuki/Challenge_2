package br.com.compass.challenge2.service;


import br.com.compass.challenge2.entity.Organizer;
import br.com.compass.challenge2.repository.OrganizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizerService {
    private OrganizerRepository organizerRepository;
    @Autowired
    public OrganizerService(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    public Organizer findById(Long id) {
        return organizerRepository.findById(id).orElse(null);
    }

    public List<Organizer> findAll() {
        return organizerRepository.findAll();
    }

    public void save(Organizer organizer) {
    }

    public void update(Organizer organizer) {
        organizerRepository.save(organizer);
    }

    public void delete(Long id) {
        organizerRepository.deleteById(id);
    }
}
