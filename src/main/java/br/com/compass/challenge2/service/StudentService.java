package br.com.compass.challenge2.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class StudentService implements CrudService<Student> {

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student save(Student student) {
        if (student.getId() == null)
            return studentRepository.save(student);

        throw new IllegalArgumentException("The \"id\" attribute is not allowed when creating a new user.");
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findById(Long id) {
        try {
            return studentRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Student does not exist with id: " + id);
        }
    }

    public Student update(Student student) {

        if (studentRepository.existsById(student.getId()))
            return studentRepository.save(student);

        throw new EntityNotFoundException("Student does not exist with id: " + student.getId());
    }

    public void delete(Student student) {
        studentRepository.delete(student);
    }

    public void deleteById(Long id) {

        try {
            studentRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Student does not exist with id: " + id);
        }
        studentRepository.deleteById(id);
    }

}
