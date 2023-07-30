package br.com.compass.challenge2.unit.service;
import br.com.compass.challenge2.config.ConfigTest;
import br.com.compass.challenge2.entity.Assessment;
import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.service.AssessmentService;
import br.com.compass.challenge2.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class AssessmentServiceTest implements ConfigTest {

    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private StudentService studentService;

    private Student student;
    private Assessment assessment;

    @BeforeEach
    public void setUp() {
        student = new Student();
        student.setName("John Doe");
        student.setEmail("john@example.com");

        student = studentService.save(student); // Salvar o estudante usando o StudentService

        assessment = new Assessment();
        assessment.setStudent(student);
        assessment.setActivityName("Math Test");
        assessment.setGrade(8.5f);
    }

    @Test
    public void testSaveAssessment() {
        Assessment savedAssessment = assessmentService.save(assessment);

        assertNotNull(savedAssessment);
        assertNotNull(savedAssessment.getId());
        assertEquals("Math Test", savedAssessment.getActivityName());
        assertEquals(8.5f, savedAssessment.getGrade());
        assertEquals(student, savedAssessment.getStudent());
    }

    @Test
    public void testSaveAssessmentWithIdThrowsException() {
        assessment.setId(1L);
        assertThrows(IllegalArgumentException.class, () -> assessmentService.save(assessment));
    }

    @Test
    public void testFindById() {
        Assessment savedAssessment = assessmentService.save(assessment);
        Assessment foundAssessment = assessmentService.findById(savedAssessment.getId());

        assertNotNull(foundAssessment);
        assertEquals(savedAssessment, foundAssessment);
    }

    @Test
    public void testFindByIdNotFound() {
        assertThrows(EntityNotFoundException.class, () -> assessmentService.findById(1L));
    }

    @Test
    public void testFindAll() {
        Assessment savedAssessment = assessmentService.save(assessment);
        List<Assessment> foundAssessments = assessmentService.findAll();
        assertFalse(foundAssessments.isEmpty());
        assertTrue(foundAssessments.contains(savedAssessment));
    }

    @Test
    public void testUpdateAssessment() {
        Assessment savedAssessment = assessmentService.save(assessment);

        savedAssessment.setActivityName("Updated Test");
        savedAssessment.setGrade(9.0f);

        Assessment updatedAssessment = assessmentService.update(savedAssessment);
        assertEquals("Updated Test", updatedAssessment.getActivityName());
        assertEquals(9.0f, updatedAssessment.getGrade());
    }

    @Test
    public void testUpdateAssessmentNotFound() {
        assessment.setId(100L);
        assertThrows(EntityNotFoundException.class, () -> assessmentService.update(assessment));
    }

    @Test
    public void testDeleteById() {
        Assessment savedAssessment = assessmentService.save(assessment);

        Assessment deletedAssessment = assessmentService.deleteById(savedAssessment.getId());

        assertNotNull(deletedAssessment);
        assertEquals(savedAssessment, deletedAssessment);

        assertThrows(EntityNotFoundException.class, () -> assessmentService.findById(savedAssessment.getId()));
    }

    @Test
    public void testDeleteByIdNotFound() {
        assertThrows(EntityNotFoundException.class, () -> assessmentService.deleteById(1L));
    }

    @Test
    public void testGetAssessmentsByStudentId() {
        Assessment savedAssessment = assessmentService.save(assessment);

        List<Assessment> foundAssessments = assessmentService.getAssessmentsByStudentId(student.getId());

        assertEquals(1, foundAssessments.size());
        assertTrue(foundAssessments.contains(savedAssessment));
    }
}
