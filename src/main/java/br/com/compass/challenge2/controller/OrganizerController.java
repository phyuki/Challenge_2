package br.com.compass.challenge2.controller;

import br.com.compass.challenge2.entity.Organizer;
import br.com.compass.challenge2.service.OrganizerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/organizers")
public class OrganizerController {
    private final OrganizerService organizerService;

    @Autowired
    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Organizer> createOrganizer(@RequestBody @Valid Organizer organizer) {
        Organizer createdOrganizer = organizerService.save(organizer);
        return getOrganizerEntityModel(createdOrganizer);
    }

    @PutMapping("/{id}")
    public EntityModel<Organizer> updateOrganizer(@PathVariable Long id, @RequestBody @Valid Organizer updatedOrganizer) {
        Organizer existingOrganizer = organizerService.findById(id);
        if (existingOrganizer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Organizer not found with id: " + id);
        }

        updatedOrganizer.setId(id);
        Organizer savedOrganizer = organizerService.update(updatedOrganizer);
        return getOrganizerEntityModel(savedOrganizer);
    }

    @GetMapping("/{id}")
    public EntityModel<Organizer> getOrganizerById(@PathVariable Long id) {
        Organizer organizer = organizerService.findById(id);
        if (organizer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Organizer not found with id: " + id);
        }

        return getOrganizerEntityModel(organizer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganizer(@PathVariable Long id) {
        Organizer existingOrganizer = organizerService.findById(id);
        if (existingOrganizer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Organizer not found with id: " + id);
        }

        organizerService.deleteById(id);
    }

    @GetMapping
    public CollectionModel<EntityModel<Organizer>> getAllOrganizers() {
        List<Organizer> organizers = organizerService.findAll();
        List<EntityModel<Organizer>> organizerResources = organizers.stream()
                .map(this::getOrganizerEntityModel)
                .collect(Collectors.toList());

        Link selfLink = linkTo(methodOn(OrganizerController.class).getAllOrganizers()).withSelfRel();
        return CollectionModel.of(organizerResources, selfLink);
    }

    // Helper method to create EntityModel for an Organizer with self-link
    private EntityModel<Organizer> getOrganizerEntityModel(Organizer organizer) {
        Link selfLink = linkTo(methodOn(OrganizerController.class).getOrganizerById(organizer.getId())).withSelfRel();
        return EntityModel.of(organizer, selfLink);
    }
}
