package br.com.compass.challenge2.unit.entity;
import br.com.compass.challenge2.entity.Group;
import br.com.compass.challenge2.entity.Organizer;
import br.com.compass.challenge2.entity.Student;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GroupTest {

    private Validator validator;
    private Group group;
    private Organizer org;
    private Student student;

    @BeforeEach
    public void setUp() {
        group = Group.builder()
                .id(1L)
                .name("Spring Boot")
                .organizers(new ArrayList<>())
                .students(new ArrayList<>())
                .build();
    }

    @Test
    public void testGetters() {
        assertEquals(1L, group.getId());
        assertEquals("Spring Boot", group.getName());
    }

    @Test
    public void testSetters() {
        student = new Student();
        student.setId(1L);
        student.setName("Pedro");
        student.setEmail("pedro@email.com");

        org = new Organizer();
        org.setId(1L);
        org.setName("João");
        org.setEmail("joão@email.com");

        group.setStudents(Arrays.asList(student));
        group.setOrganizers(Arrays.asList(org));

        assertEquals(Arrays.asList(student), group.getStudents());
        assertEquals(Arrays.asList(org), group.getOrganizers());
    }

    @Test
    public void testStudentGetters() {
        student = new Student();
        student.setId(1L);
        student.setName("Pedro");
        student.setEmail("pedro@email.com");
        group.setStudents(Arrays.asList(student));

        assertEquals(1L, group.getStudents().get(0).getId());
        assertEquals("Pedro", group.getStudents().get(0).getName());
        assertEquals("pedro@email.com", group.getStudents().get(0).getEmail());
    }

    @Test
    public void testOrganizerGetters() {
        org = new Organizer();
        org.setId(1L);
        org.setName("João");
        org.setEmail("joao@email.com");
        group.setOrganizers(Arrays.asList(org));

        assertEquals(1L, group.getOrganizers().get(0).getId());
        assertEquals("João", group.getOrganizers().get(0).getName());
        assertEquals("joao@email.com", group.getOrganizers().get(0).getEmail());
    }

    @Test
    public void NoConstraintViolationsWithNullStudentsAndOrganizers() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Group>> violations = validator.validate(group);
        assertEquals(0, violations.size());
    }

    @Test
    public void ConstraintViolationNotBlankOnGroupName() {
        group.setName("");

        validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Group>> violations = validator.validate(group);
        assertEquals(1, violations.size());
        assertEquals("name", violations.iterator().next().getPropertyPath().toString());
    }

}
