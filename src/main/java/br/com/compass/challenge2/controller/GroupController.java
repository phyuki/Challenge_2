package br.com.compass.challenge2.controller;

import br.com.compass.challenge2.entity.Group;
import br.com.compass.challenge2.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<Group>> findAllGroups() {

        List<Group> groups = groupService.findAll();

        groups.stream().map(group -> group.add(
                linkTo(methodOn(GroupController.class).findGroupById(group.getId())).withSelfRel(),
                linkTo(methodOn(GroupController.class).updateGroup(group.getId(), group))
                        .withRel("update"))).toList();

        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> findGroupById(@PathVariable Long id) {

        Group group = groupService.findById(id);

        group.add(linkTo(methodOn(GroupController.class).findGroupById(id)).withSelfRel(),
                linkTo(methodOn(GroupController.class).findAllGroups()).withRel("all_groups"),
                linkTo(methodOn(GroupController.class).updateGroup(id, group)).withRel("update"),
                linkTo(methodOn(GroupController.class).deleteGroupById(id)).withRel("delete"));

        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@Valid @RequestBody Group group) {
        group.setId(0L);
        Group newGroup = groupService.save(group);

        newGroup.add(
                linkTo(methodOn(GroupController.class).findGroupById(newGroup.getId())).withSelfRel(),
                linkTo(methodOn(GroupController.class).findAllGroups()).withRel("all_groups"),
                linkTo(methodOn(GroupController.class).updateGroup(newGroup.getId(), group))
                        .withRel("update"),
                linkTo(methodOn(GroupController.class).deleteGroupById(newGroup.getId())).withRel("delete"));

        return new ResponseEntity<>(newGroup, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable Long id, @Valid @RequestBody Group group) {
        group.setId(id);
        Group updatedGroup = groupService.update(group);

        updatedGroup.add(linkTo(methodOn(GroupController.class).findGroupById(id)).withSelfRel(),
                linkTo(methodOn(GroupController.class).findAllGroups()).withRel("all_groups"));

        return new ResponseEntity<>(updatedGroup, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Group> deleteGroupById(@PathVariable Long id) {
        Group group = groupService.findById(id);

        Group deletedGroup = groupService.deleteById(id);
        deletedGroup.add(linkTo(methodOn(GroupController.class).findAllGroups()).withRel("all_groups"));
        return new ResponseEntity<>(deletedGroup, HttpStatus.OK);
    }
}
