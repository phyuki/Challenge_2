package br.com.compass.challenge2.unit.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import br.com.compass.challenge2.entity.Squad;
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
    public void testInvalidSquadWithNullGroupsAndStudents() {       
        Squad squad = new Squad();
        squad.setId(1L);
        squad.setSquadName("Squad 1");
                
        Set<ConstraintViolation<Squad>> violations = validator.validate(squad);
        assertTrue(violations.isEmpty());
    }
}
  