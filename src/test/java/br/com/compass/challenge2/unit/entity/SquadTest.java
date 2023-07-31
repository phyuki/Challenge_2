package br.com.compass.challenge2.unit.entity;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.compass.challenge2.entity.Group;
import br.com.compass.challenge2.entity.Squad;
import br.com.compass.challenge2.entity.Student;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class SquadTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testValidSquadEntity() {
     
        Squad squad = new Squad();
        squad.setId(1L);
        squad.setSquadName("Squad 1");

     
        Set<ConstraintViolation<Squad>> violations = validator.validate(squad);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidSquadName() {        
        Squad squad = new Squad();
        squad.setId(1L);
        squad.setSquadName("");       
        Set<ConstraintViolation<Squad>> violations = validator.validate(squad);
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("Nome do squad não pode ser vazio", violations.iterator().next().getMessage());
    }

    @Test
    public void testValidSquadWithGroupsAndStudents() {
    
        Squad squad = new Squad();
        squad.setId(1L);
        squad.setSquadName("Squad 1");

        Group group1 = new Group();
        group1.setId(1L);
        group1.setName("Group 1");

        Group group2 = new Group();
        group2.setId(2L);
        group2.setName("Group 2");
       
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Marcos");
        student1.setEmail("marcos123doe@example.com");
        student1.setGroup(group1);

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Joao");
        student2.setEmail("joao123@example.com");
        student2.setGroup(group2);

        
        squad.getGroups().add(group1);
        squad.getGroups().add(group2);
        squad.getStudents().add(student1);
        squad.getStudents().add(student2);

        
        Set<ConstraintViolation<Squad>> violations = validator.validate(squad);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidSquadWithNullGroupsAndStudents() {       
        Squad squad = new Squad();
        squad.setId(1L);
        squad.setSquadName("Squad 1");

       
        Set<ConstraintViolation<Squad>> violations = validator.validate(squad);
        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());

        assertThat(violations)
                .extracting(ConstraintViolation::getMessage)
                .contains("Grupo não pode estar vazio.", "Estudantes não pode estar vazio");
    }
}
  