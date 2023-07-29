package br.com.compass.challenge2.controller;

import br.com.compass.challenge2.entity.Group;
import br.com.compass.challenge2.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/groups")
public class GroupController {

    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupService.findAll();
        if (!groups.isEmpty()) {
            return new ResponseEntity<>(groups, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long id) {
        Group group = groupService.findById(id);
        if (group != null) {
            return new ResponseEntity<>(group, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@Valid @RequestBody Group group) {
        group.setId(0L);
        Group newGroup = groupService.save(group);
        return new ResponseEntity<>(newGroup, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroupById(@PathVariable Long id, @Valid @RequestBody Group group) {
        Group existingGroup = groupService.findById(id);
        if (existingGroup != null) {
            group.setId(id);
            Group updatedGroup = groupService.update(group);
            return new ResponseEntity<>(updatedGroup, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupById(@PathVariable Long id) {
        Group existingGroup = groupService.findById(id);
        if (existingGroup != null) {
            groupService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
