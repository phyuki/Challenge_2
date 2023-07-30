package br.com.compass.challenge2.unit.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import br.com.compass.challenge2.entity.Squad;
import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.repository.SquadRepository;

import static org.junit.jupiter.api.Assertions.*;

public class SquadTest {

    private Squad squad;

    @BeforeEach
    public void setUp() {
        squad = new Squad();
    }

    @Test
    public void testSetAndGetId() {
        Long id = 1L;
        squad.setId(id);
        assertEquals(id, squad.getId());
    }

    @Test
    public void testSetAndGetSquadName() {
        String squadName = "Teste Squad";
        squad.setSquadName(squadName);
        assertEquals(squadName, squad.getSquadName());
    }

    @Test
    public void testSetAndGetStudents() {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Estudante 1");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Estudante 2");

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        squad.setStudents(students);

        assertNotNull(squad.getStudents());
        assertEquals(2, squad.getStudents().size());
    }

    @Test
    public void testAddStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Student 1");

        squad.addStudent(student);

        assertNotNull(squad.getStudents());
        assertEquals(1, squad.getStudents().size());
        assertEquals(student, squad.getStudents().get(0));
    }

    @Test
    public void testRemoveStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Student 1");

        squad.addStudent(student);
        squad.removeStudent(student);

        assertNotNull(squad.getStudents());
        assertEquals(0, squad.getStudents().size());
    }
}
