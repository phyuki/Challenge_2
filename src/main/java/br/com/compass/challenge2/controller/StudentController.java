package br.com.compass.challenge2.controller;

import br.com.compass.challenge2.entity.Assessment;
import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.service.AssessmentService;
import br.com.compass.challenge2.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {
    private  StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student studente) {
        Student createdStudent = studentService.save(studente);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }
}
