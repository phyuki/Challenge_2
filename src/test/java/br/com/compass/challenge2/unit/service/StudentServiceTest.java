package br.com.compass.challenge2.unit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import br.com.compass.challenge2.config.ConfigTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.repository.StudentRepository;
import br.com.compass.challenge2.service.StudentService;
import jakarta.persistence.EntityNotFoundException;


public class StudentServiceTest implements ConfigTest {

    @MockBean
    private StudentRepository studentRepository;

    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        studentService = new StudentService(studentRepository);
    }

    @Test
    public void testSaveNewStudent() {
        Student student = new Student();
        student.setName("Gabriel");

        when(studentRepository.save(any())).thenAnswer(invocation -> {
            Student savedStudent = invocation.getArgument(0);
            savedStudent.setId(1L);
            return savedStudent;
        });

        Student savedStudent = studentService.save(student);

        assertNotNull(savedStudent.getId());
        assertEquals("Gabriel", savedStudent.getName());
    }

    @Test
    public void testSaveExistingStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Gabriel");

        assertThrows(IllegalArgumentException.class, () -> studentService.save(student));
    }

    @Test
    public void testFindAllStudents() {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Gabriel");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Guilherme");

        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));

        List<Student> students = studentService.findAll();

        assertEquals(2, students.size());
        assertEquals("Gabriel", students.get(0).getName());
        assertEquals("Guilherme", students.get(1).getName());
    }

    @Test
    public void testFindByIdExistingStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Gabriel");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student foundStudent = studentService.findById(1L);

        assertEquals(1L, foundStudent.getId());
        assertEquals("Gabriel", foundStudent.getName());
    }

    @Test
    public void testFindByIdNonExistingStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentService.findById(1L));
    }

    @Test
    public void testUpdateExistingStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Gabriel");

        when(studentRepository.existsById(1L)).thenReturn(true);
        when(studentRepository.save(any())).thenReturn(student);

        Student updatedStudent = studentService.update(student);

        assertEquals(1L, updatedStudent.getId());
        assertEquals("Gabriel", updatedStudent.getName());
    }

    @Test
    public void testUpdateNonExistingStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Gabriel");

        when(studentRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> studentService.update(student));
    }

    @Test
    public void testDeleteByIdExistingStudent() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Gabriel");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student deletedStudent = studentService.deleteById(1L);

        assertEquals(1L, deletedStudent.getId());
        assertEquals("Gabriel", deletedStudent.getName());

        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteByIdNonExistingStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentService.deleteById(1L));
    }
}