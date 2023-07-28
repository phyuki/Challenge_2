package br.com.compass.challenge2.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService implements CrudService<Student>{

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Student update(Student student) {

        if (studentRepository.existsById(student.getId()))
            return studentRepository.save(student);

        throw new IllegalArgumentException();
    }

    public void delete(Student student) {
        studentRepository.delete(student);
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

}
