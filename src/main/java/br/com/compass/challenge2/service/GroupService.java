package br.com.compass.challenge2.service;

import br.com.compass.challenge2.entity.Group;
import br.com.compass.challenge2.entity.Organizer;
import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.records.GroupRecord;
import br.com.compass.challenge2.repository.GroupRepository;
import br.com.compass.challenge2.repository.OrganizerRepository;
import br.com.compass.challenge2.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GroupService implements CrudService<Group> {

    private final GroupRepository groupRepository;
    private final OrganizerRepository organizerRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, OrganizerRepository organizerRepository,
                        StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.organizerRepository = organizerRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Group findById(Long id) {
        try {
            return groupRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Group does not exist with id: " + id);
        }
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Transactional
    @Override
    public Group save(Group group) {

        Group savedGroup = groupRepository.save(group);
        List<Organizer> organizersInGroup = new ArrayList<>(savedGroup.getOrganizers());
        List<Student> studentsInGroup = new ArrayList<>(savedGroup.getStudents());
        for(Organizer org : organizersInGroup){
            org.getGroups().add(savedGroup);
        }
        for(Student student : studentsInGroup){
            student.setGroup(savedGroup);
        }
        organizerRepository.saveAll(organizersInGroup);
        studentRepository.saveAll(studentsInGroup);
        return savedGroup;
    }

    @Override
    public Group update(Group group) {
        if (groupRepository.existsById(group.getId())) {
            return groupRepository.save(group);
        } else {
            throw new EntityNotFoundException("Group with id " + group.getId() + " not found.");
        }
    }

    @Transactional
    @Override
    public Group deleteById(Long id) {
        Group group;
        try {
            group = groupRepository.findById(id).get();
            for(Student student : group.getStudents()){
                student.setGroup(null);
            }
            List<Organizer> organizersInGroup = new ArrayList<>(group.getOrganizers());
            for(Organizer org : organizersInGroup){
                org.getGroups().remove(group);
            }
            group.getOrganizers().clear();
            System.out.println(group.getId());
            groupRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Group does not exist with id: " + id);
        }

        return group;
    }
}
