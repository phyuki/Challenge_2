package br.com.compass.challenge2.controller;

import br.com.compass.challenge2.entity.Group;
import br.com.compass.challenge2.entity.Organizer;
import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.records.GroupRecord;
import br.com.compass.challenge2.service.GroupService;
import br.com.compass.challenge2.service.OrganizerService;
import br.com.compass.challenge2.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private GroupService groupService;
    private OrganizerService organizerService;
    private StudentService studentService;

    @Autowired
    public GroupController(GroupService groupService, OrganizerService organizerService,
                           StudentService studentService) {
        this.groupService = groupService;
        this.organizerService = organizerService;
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Group>> findAllGroups() {

        List<Group> groups = groupService.findAll();

        groups.stream().map(group -> group.add(
                linkTo(methodOn(GroupController.class).findGroupById(group.getId())).withSelfRel()))
                .toList();

        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> findGroupById(@PathVariable Long id) {

        Group group = groupService.findById(id);

        group.add(linkTo(methodOn(GroupController.class).findGroupById(id)).withSelfRel(),
                linkTo(methodOn(GroupController.class).findAllGroups()).withRel("all_groups"),
                linkTo(methodOn(GroupController.class).deleteGroupById(id)).withRel("delete"));

        return new ResponseEntity<>(group, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(@Valid @RequestBody GroupRecord groupRecord) {

        List<Student> students = new ArrayList<>();
        List<Organizer> organizers = new ArrayList<>();
        if(groupRecord.studentIDs() != null) {
            for (Long studentID : groupRecord.studentIDs()) {
                students.add(studentService.findById(studentID));
            }
        }
        if(groupRecord.organizerIDs() != null){
            for(Long organizerID : groupRecord.organizerIDs()){
                organizers.add(organizerService.findById(organizerID));
            }
        }

        Group newGroup = new Group(0L, groupRecord.name(), students, organizers);
        groupService.save(newGroup);

        newGroup.add(
                linkTo(methodOn(GroupController.class).findGroupById(newGroup.getId())).withSelfRel(),
                linkTo(methodOn(GroupController.class).findAllGroups()).withRel("all_groups"),
                linkTo(methodOn(GroupController.class).deleteGroupById(newGroup.getId())).withRel("delete"));

        return new ResponseEntity<>(newGroup, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Group> updateGroup(@PathVariable Long id, @Valid @RequestBody GroupRecord groupRecord) {

        List<Student> students = new ArrayList<>();
        List<Organizer> organizers = new ArrayList<>();
        if(groupRecord.studentIDs() != null) {
            for (Long studentID : groupRecord.studentIDs()) {
                students.add(studentService.findById(studentID));
            }
        }
        if(groupRecord.organizerIDs() != null){
            for(Long organizerID : groupRecord.organizerIDs()){
                organizers.add(organizerService.findById(organizerID));
            }
        }

        Group group = new Group(id, groupRecord.name(), students, organizers);
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
