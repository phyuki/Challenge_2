package br.com.compass.challenge2.unit.service;

import br.com.compass.challenge2.config.ConfigTest;
import br.com.compass.challenge2.entity.Group;
import br.com.compass.challenge2.entity.Organizer;
import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.repository.GroupRepository;
import br.com.compass.challenge2.repository.OrganizerRepository;
import br.com.compass.challenge2.repository.StudentRepository;
import br.com.compass.challenge2.service.GroupService;
import br.com.compass.challenge2.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class GroupServiceTest implements ConfigTest {

    private GroupService groupService;
    @MockBean
    private GroupRepository groupRepository;
    @MockBean
    private OrganizerRepository organizerRepository;
    @MockBean
    private StudentRepository studentRepository;

    private Group group;

    @BeforeEach
    public void setup(){
        groupService = new GroupService(groupRepository, organizerRepository, studentRepository);
        group = Group.builder()
                .id(1L)
                .name("Spring Boot")
                .organizers(new ArrayList<>())
                .students(new ArrayList<>())
                .build();
    }

    @Test
    void saveGroupWithoutStudentsNeitherOrganizers() {
        when(groupRepository.save(any())).thenReturn(group);
        Group savedGroup = groupService.save(group);

        verify(groupRepository, times(1)).save(any(Group.class));
        assertEquals("Spring Boot", savedGroup.getName());
    }

    @Test
    void saveGroupWithStudentsAndOrganizers() {
        Student student1 = new Student();
        student1.setName("Pedro");
        student1.setEmail("pedro@email.com");

        Student student2 = new Student();
        student2.setName("João");
        student2.setEmail("joao@email.com");
        group.setStudents(new ArrayList<>(Arrays.asList(student1, student2)));

        Organizer org1 = new Organizer();
        org1.setName("Renan");
        org1.setEmail("renan@email.com");
        org1.setGroups(new ArrayList<Group>());

        Organizer org2 = new Organizer();
        org2.setName("Maria");
        org2.setEmail("maria@email.com");
        org2.setGroups(new ArrayList<Group>());

        List<Organizer> orgs = new ArrayList<>();
        orgs.add(org1);
        orgs.add(org2);
        group.setOrganizers(orgs);

        when(groupRepository.save(any())).thenReturn(group);
        Group savedGroup = groupService.save(group);

        assertThat(savedGroup).usingRecursiveComparison().isEqualTo(group);
        verify(groupRepository, times(1)).save(any(Group.class));
    }

    @Test
    void findExistingGroup() {
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        Group findGroup = groupService.findById(1L);

        assertEquals(1L, findGroup.getId());
        assertEquals("Spring Boot", findGroup.getName());
        verify(groupRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void findNonExistingGroup() {
        when(groupRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> groupService.findById(1L));
    }

    @Test
    void findAllGroups() {
        Group newGroup = Group.builder()
                .id(2L)
                .name("AWS")
                .organizers(new ArrayList<>())
                .students(new ArrayList<>())
                .build();

        List<Group> allGroups = new ArrayList<>(Arrays.asList(group, newGroup));
        when(groupRepository.findAll()).thenReturn(allGroups);
        List<Group> findAllGroups = groupService.findAll();

        assertThat(findAllGroups).usingRecursiveComparison().isEqualTo(allGroups);
        verify(groupRepository, times(1)).findAll();
    }

    @Test
    void updateExistingGroup() {
        when(groupRepository.existsById(1L)).thenReturn(true);
        when(groupRepository.save(any())).thenReturn(group);

        Group updatedGroup = groupService.update(group);

        assertEquals(1L, updatedGroup.getId());
        assertEquals("Spring Boot", updatedGroup.getName());
        verify(groupRepository, times(1)).save(group);
    }

    @Test
    void updateNonExistingGroup() {
        when(groupRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> groupService.update(group));
    }

    @Test
    void deleteGroupWithoutStudentsNeitherOrganizers() {

        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        Group deletedGroup = groupService.deleteById(1L);

        assertThat(deletedGroup).usingRecursiveComparison().isEqualTo(group);
        verify(groupRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteGroupWithStudentsAndOrganizers() {

        Student student1 = new Student();
        student1.setName("Pedro");
        student1.setEmail("pedro@email.com");

        Student student2 = new Student();
        student2.setName("João");
        student2.setEmail("joao@email.com");
        group.setStudents(new ArrayList<>(Arrays.asList(student1, student2)));

        Organizer org1 = new Organizer();
        org1.setName("Renan");
        org1.setEmail("renan@email.com");
        org1.setGroups(new ArrayList<Group>());

        Organizer org2 = new Organizer();
        org2.setName("Maria");
        org2.setEmail("maria@email.com");
        org2.setGroups(new ArrayList<Group>());

        group.setOrganizers(new ArrayList<>(Arrays.asList(org1, org2)));

        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));

        Group deletedGroup = groupService.deleteById(1L);

        assertThat(deletedGroup).usingRecursiveComparison().isEqualTo(group);
        verify(groupRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteNonExistingGroup() {

        when(groupRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> groupService.deleteById(1L));
        verify(groupRepository, times(1)).findById(any(Long.class));

    }
}