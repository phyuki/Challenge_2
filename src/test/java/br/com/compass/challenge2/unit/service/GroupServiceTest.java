package br.com.compass.challenge2.unit.service;

import br.com.compass.challenge2.entity.Group;
import br.com.compass.challenge2.entity.Organizer;
import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.service.GroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GroupServiceTest {

    @Autowired
    GroupService groupService;

    @Test
    void saveGroupWithoutStudentsNeitherOrganizers() {
        Group groupBoot = new Group();
        groupBoot.setName("Spring Boot");
        groupBoot.setOrganizers(new HashSet<Organizer>());
        groupService.save(groupBoot);
    }

    @Test
    void saveGroupWithStudentsAndOrganizers() {
        Group groupBoot = new Group();
        groupBoot.setName("Spring Boot");

        Student student1 = new Student();
        student1.setName("Pedro");
        student1.setEmail("pedro@email.com");

        Student student2 = new Student();
        student2.setName("João");
        student2.setEmail("joao@email.com");

        groupBoot.setStudents(new ArrayList<Student>());
        groupBoot.getStudents().add(student1);
        groupBoot.getStudents().add(student2);

        Organizer org1 = new Organizer();
        org1.setName("Renan");
        org1.setEmail("renan@email.com");
        org1.setGroups(new HashSet<Group>());

        Organizer org2 = new Organizer();
        org2.setName("Maria");
        org2.setEmail("maria@email.com");
        org2.setGroups(new HashSet<Group>());

        groupBoot.setOrganizers(new HashSet<Organizer>());
        groupBoot.getOrganizers().add(org1);
        groupBoot.getOrganizers().add(org2);

        groupService.save(groupBoot);
    }

    @Test
    void findById() {
        Group groupBoot = new Group();
        groupBoot.setName("Spring Boot");
        groupBoot.setOrganizers(new HashSet<Organizer>());
        groupService.save(groupBoot);

        Group groupAWS = new Group();
        groupBoot.setName("AWS");
        groupBoot.setOrganizers(new HashSet<Organizer>());
        groupService.save(groupBoot);

        assertEquals(groupService.findById(1L).getName(), "Spring Boot");
        assertEquals(groupService.findById(2L).getName(), "AWS");
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}