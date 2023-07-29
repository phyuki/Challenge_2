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

    @Override
    public Student save(Student student) {
        if (student.getId() == null)
            return studentRepository.save(student);

        throw new IllegalArgumentException("The \"id\" attribute is not allowed when creating a new user.");
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {
        try {
            return studentRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Student does not exist with id: " + id);
        }
    }

    @Override
    public Student update(Student student) {

        if (studentRepository.existsById(student.getId()))
            return studentRepository.save(student);

        throw new EntityNotFoundException("Student does not exist with id: " + student.getId());
    }

    @Override
    public Student deleteById(Long id) {
        Student student;
        try {
            student = studentRepository.findById(id).get();
            studentRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Student does not exist with id: " + id);
        }

        return student;
    }

}
