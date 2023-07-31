package br.com.compass.challenge2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class StudentService implements CrudService<Student> {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student save(Student student) {
        if (student.getId() == null)
            return studentRepository.save(student);

        throw new IllegalArgumentException("The \"id\" attribute is not allowed when creating a new student.");
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {
        Student student;

        if (studentRepository.findById(id).isPresent())
            student = studentRepository.findById(id).get();
        else
            throw new EntityNotFoundException("Student does not exist with id: " + id);

        return student;
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

        if (studentRepository.findById(id).isPresent()) {
            student = studentRepository.findById(id).get();
            studentRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Student does not exist with id: " + id);
        }

        return student;
    }

}
