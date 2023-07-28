package br.com.compass.challenge2.service;


import br.com.compass.challenge2.entity.Organizer;
import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.repository.OrganizerRepository;

import java.util.List;
import java.util.Optional;

public class OrganizerService {
    private final OrganizerRepository organizerRepository;

    public OrganizerService(OrganizerRepository organizerRepository) {
        this.organizerRepository = organizerRepository;
    }

    public Optional<Student> findById(Long id) {
        return organizerRepository.findById(id);
    }

    public List<Student> findAll() {
        return organizerRepository.findAll();
    }

    public void save(Organizer organizer) {
    }

    public void update(Organizer organizer) {
        organizerRepository.update(organizer);
    }

    public void delete(Long id) {
        organizerRepository.delete(id);
    }
}
