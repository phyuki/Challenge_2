package br.com.compass.challenge2.unit.entity;

import br.com.compass.challenge2.entity.Assessment;
import br.com.compass.challenge2.entity.Group;
import br.com.compass.challenge2.entity.Squad;
import br.com.compass.challenge2.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class StudentTest {

    private Student student;
    private Group group;
    private Squad squad;
    private List<Assessment> assessments;
    private Assessment assessment1;
    private Assessment assessment2;


    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setName("Gabriel");
        student.setEmail("gabriel@guilherme.com");

        group = new Group();
        group.setId(1L);
        group.setName("Spring Boot");
        student.setGroup(group);
        group.setStudents(Arrays.asList(student));

        squad = new Squad();
        squad.setId(1L);
        squad.setSquadName("JEDGP");
        squad.setStudents(Arrays.asList(student));
        student.setSquad(squad);

        assessment1 = new Assessment();
        assessment1.setId(1L);
        assessment1.setActivityName("Assessment 1");
        assessment1.setStudent(student);
        assessment1.setGrade(10.0F);

        assessment2 = new Assessment();
        assessment2.setId(2L);
        assessment2.setActivityName("Assessment 2");
        assessment2.setStudent(student);
        assessment2.setGrade(7.5F);

        student.setAssessments(Arrays.asList(assessment1,assessment2));

        assessments = new ArrayList<>();
        assessments.add(assessment1);
        assessments.add(assessment2);

        student.setAssessments(assessments);
    }

    @Test
    void testStudentGetters() {
        assertEquals(1L, student.getId());
        assertEquals("Gabriel", student.getName());
        assertEquals("gabriel@guilherme.com", student.getEmail());
        assertEquals(squad, student.getSquad());
        assertEquals(group, student.getGroup());
        assertEquals(assessment1, student.getAssessments().get(0));
        assertEquals(assessment2, student.getAssessments().get(1));
    }

    @Test
    void testSquadGetters() {
        assertEquals(1L, squad.getId());
        assertEquals("JEDGP", squad.getSquadName());
        assertEquals(student, squad.getStudents().get(0));
    }

    @Test
    void testGroupGetters() {
        assertEquals(1L, group.getId());
        assertEquals("Spring Boot", group.getName());
        assertEquals(student, group.getStudents().get(0));
    }

    @Test
    void testAssessments() {
        assertEquals(1L, assessments.get(0).getId());
        assertEquals("Assessment 1", assessments.get(0).getActivityName());
        assertEquals(student, assessments.get(0).getStudent());
        assertEquals(10.0F, assessments.get(0).getGrade());

        assertEquals(2L, assessments.get(1).getId());
        assertEquals("Assessment 2", assessments.get(1).getActivityName());
        assertEquals(student, assessments.get(1).getStudent());
        assertEquals(7.5F, assessments.get(1).getGrade());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Student student1 = new Student(1L, "Gabriel", "gabriel@guilherme.com", group, squad, assessments);
        Student student2 = new Student(1L, "Gabriel", "gabriel@guilherme.com", group, squad, assessments);

        assertTrue(student1.equals(student2));
        assertTrue(student2.equals(student1));

        assertEquals(student1.hashCode(), student2.hashCode());
    }
}
