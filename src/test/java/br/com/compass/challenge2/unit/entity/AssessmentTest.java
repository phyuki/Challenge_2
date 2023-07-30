package br.com.compass.challenge2.unit.entity;
import br.com.compass.challenge2.entity.Assessment;
import br.com.compass.challenge2.entity.Student;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AssessmentTest {
    private Validator validator;
    private Assessment assessment;
    private Student student;

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();

        student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setEmail("john@example.com");

        assessment = new Assessment();
        assessment.setId(1L);
        assessment.setStudent(student);
        assessment.setActivityName("Math Test");
        assessment.setGrade(8.5f);
    }

    @Test
    public void testAssessmentInstance() {
        assertNotNull(assessment);
    }

    @Test
    public void testGetters() {
        assertEquals(1L, assessment.getId());
        assertEquals(student, assessment.getStudent());
        assertEquals("Math Test", assessment.getActivityName());
        assertEquals(8.5f, assessment.getGrade(), 0.001); // Use delta for float comparison
    }

    @Test
    public void testSetters() {
        Student newStudent = new Student();
        newStudent.setId(2L);
        newStudent.setName("Jane Smith");
        newStudent.setEmail("jane@example.com");

        assessment.setId(2L);
        assessment.setStudent(newStudent);
        assessment.setActivityName("History Test");
        assessment.setGrade(9.0f);

        assertEquals(2L, assessment.getId());
        assertEquals(newStudent, assessment.getStudent());
        assertEquals("History Test", assessment.getActivityName());
        assertEquals(9.0f, assessment.getGrade(), 0.001); // Use delta for float comparison
    }

    @Test
    public void testNotNullAnnotationOnStudent() {
        assessment.setStudent(null);

        // Should fail validation because 'student' is marked as @NotNull
        Set<ConstraintViolation<Assessment>> violations = validator.validate(assessment);
        assertEquals(1, violations.size());
        assertEquals("student", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    public void testNotBlankAnnotationOnActivityName() {
        assessment.setActivityName("");

        // Should fail validation because 'activityName' is marked as @NotBlank
        Set<ConstraintViolation<Assessment>> violations = validator.validate(assessment);
        assertEquals(1, violations.size());
        assertEquals("activityName", violations.iterator().next().getPropertyPath().toString());
    }



}
