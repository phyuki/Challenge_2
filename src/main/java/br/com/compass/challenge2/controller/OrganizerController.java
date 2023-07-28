package br.com.compass.challenge2.controller;

import br.com.compass.challenge2.entity.Organizer;
import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.service.OrganizerService;

import java.util.List;
import java.util.Optional;

public class OrganizerController {
    private final OrganizerService organizerService;

    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    // Endpoint to get a specific organizer by id
    public Optional<Student> getOrganizerById(Long id) {
        return organizerService.findById(id);
    }

    // Endpoint to get all organizers
    public List<Student> getAllOrganizers() {
        return organizerService.findAll();
    }

    // Endpoint to create a new organizer
    public void createOrganizer(Organizer organizer) {
        organizerService.save(organizer);
    }

    // Endpoint to update an existing organizer
    public void updateOrganizer(Organizer organizer) {
        organizerService.update(organizer);
    }

    // Endpoint to delete an organizer by id
    public void deleteOrganizer(Long id) {
        organizerService.delete(id);
    }
}
