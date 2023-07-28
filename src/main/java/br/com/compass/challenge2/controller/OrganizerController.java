package br.com.compass.challenge2.controller;

import br.com.compass.challenge2.entity.Organizer;
import br.com.compass.challenge2.service.OrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizer")
public class OrganizerController {
    private final OrganizerService organizerService;

    @Autowired
    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    @GetMapping("/{id}") // Endpoint to get a specific organizer by id
    public Organizer getOrganizerById(@PathVariable Long id) {
        return organizerService.findById(id);
    }

    @GetMapping // Endpoint to get all organizers
    public List<Organizer> getAllOrganizers() {
        return organizerService.findAll();
    }

    @PostMapping // Endpoint to create a new organizer
    public void createOrganizer(@RequestBody Organizer organizer) {
        organizerService.save(organizer);
    }

    @PutMapping("/{id}") // Endpoint to update an existing organizer
    public void updateOrganizer(@PathVariable Long id, @RequestBody Organizer organizer) {
        organizer.setId(id); // Make sure the provided ID is set in the organizer object
        organizerService.update(organizer);
    }

    @DeleteMapping("/{id}") // Endpoint to delete an organizer by id
    public void deleteOrganizer(@PathVariable Long id) {
        organizerService.delete(id);
    }
}
