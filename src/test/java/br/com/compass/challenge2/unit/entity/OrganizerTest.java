package br.com.compass.challenge2.unit.entity;

import br.com.compass.challenge2.entity.Group;
import br.com.compass.challenge2.entity.Organizer;
import br.com.compass.challenge2.entity.Role;
import br.com.compass.challenge2.entity.Student;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class OrganizerTest {

    private Validator validator;
    private Organizer org;
    private Group group;

    @BeforeEach
    public void setUp() {
        org = new Organizer();
        org.setId(1L);
        org.setName("Victor");
        org.setEmail("victor@email.com");
        org.setGroups(new ArrayList<>());
        org.setRoles(new ArrayList<>());
    }

    @Test
    public void testGetters() {
        assertEquals(1L, org.getId());
        assertEquals("Victor", org.getName());
        assertEquals("victor@email.com", org.getEmail());
    }

    @Test
    public void testSetters() {
        group = Group.builder()
                .id(1L)
                .name("Spring Boot")
                .organizers(new ArrayList<>())
                .students(new ArrayList<>())
                .build();

        org.setGroups(Arrays.asList(group));
        org.setRoles(Arrays.asList(Role.COORDINATOR, Role.SCRUM_MASTER));

        assertEquals(Arrays.asList(group), org.getGroups());
        assertEquals(Arrays.asList(Role.COORDINATOR, Role.SCRUM_MASTER), org.getRoles());
    }

    @Test
    public void testGroupGetters() {
        Organizer newOrg = new Organizer(1L, "Maria", "maria@email.com",
                new ArrayList<>(), new ArrayList<>());
        group = Group.builder()
                .id(1L)
                .name("Spring Boot")
                .organizers(new ArrayList<>())
                .students(new ArrayList<>())
                .build();
        newOrg.setGroups(Arrays.asList(group));


        assertEquals(1L, newOrg.getGroups().get(0).getId());
        assertEquals("Spring Boot", newOrg.getGroups().get(0).getName());
    }

    @Test
    public void testGettingRoles() {
        org.setRoles(Arrays.asList(Role.COORDINATOR, Role.SCRUM_MASTER));

        assertEquals(Role.COORDINATOR, org.getRoles().get(0));
        assertEquals(Role.SCRUM_MASTER, org.getRoles().get(1));
    }

    @Test
    public void noConstraintViolationsWithNullGroupsAndRoles() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Organizer>> violations = validator.validate(org);
        assertEquals(0, violations.size());
    }

    @Test
    public void constraintViolationNotBlankOnNameAndEmail() {
        org.setName("");
        org.setEmail("");

        validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Organizer>> violations = validator.validate(org);
        assertEquals(2, violations.size());
    }

}
