package br.com.compass.challenge2.controller;

import br.com.compass.challenge2.entity.Organizer;
import br.com.compass.challenge2.service.OrganizerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<Organizer> createOrganizer(@Valid @RequestBody Organizer organizer) {
        Organizer createdOrganizer = organizerService.save(organizer);
        return new ResponseEntity<>(createdOrganizer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Organizer> updateOrganizer(@Valid @PathVariable Long id, @RequestBody Organizer organizer) {
        Organizer existingOrganizer = organizerService.findById(id);
        if (existingOrganizer != null) {
            organizer.setId(id);
            Organizer updatedOrganizer = organizerService.update(organizer);
            return new ResponseEntity<>(updatedOrganizer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organizer> getOrganizerById(@PathVariable Long id) {
        Organizer organizer = organizerService.findById(id);
        if (organizer != null) {
            return new ResponseEntity<>(organizer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizer(@PathVariable Long id) {
        Organizer existingOrganizer = organizerService.findById(id);
        if (existingOrganizer != null) {
            organizerService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Organizer>> getAllOrganizers() {
        List<Organizer> organizers = organizerService.findAll();
        return new ResponseEntity<>(organizers, HttpStatus.OK);
    }
}
