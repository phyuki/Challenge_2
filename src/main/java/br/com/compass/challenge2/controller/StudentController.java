package br.com.compass.challenge2.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.ArrayList;
import java.util.List;

import br.com.compass.challenge2.entity.Assessment;
import br.com.compass.challenge2.entity.Organizer;
import br.com.compass.challenge2.records.StudentRecord;
import br.com.compass.challenge2.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.service.StudentService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final AssessmentService assessService;

    @Autowired
    public StudentController(StudentService studentService, AssessmentService assessService) {
        this.studentService = studentService;
        this.assessService = assessService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentRecord studentRecord) {

        Student createdStudent = new Student(null, studentRecord.name(), studentRecord.email(), null);
        studentService.save(createdStudent);

        createdStudent.add(
                linkTo(methodOn(StudentController.class).findStudentById(createdStudent.getId())).withSelfRel(),
                linkTo(methodOn(StudentController.class).deleteStudent(createdStudent.getId())).withRel("delete"),
                linkTo(methodOn(StudentController.class).findAllStudents()).withRel("all_students"));

        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Student>> findAllStudents() {
        List<Student> students = studentService.findAll();

        students = students.stream().map(student -> student.add(
                linkTo(methodOn(StudentController.class).findStudentById(student.getId())).withSelfRel(),
                linkTo(methodOn(StudentController.class).deleteStudent(student.getId())).withRel("delete"),
                linkTo(methodOn(AssessmentController.class).getAssessmentsByStudentId(student.getId())).withRel("assessments"))).toList();

        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> findStudentById(@PathVariable Long id) {
        Student student = studentService.findById(id);
        student.add(linkTo(methodOn(StudentController.class).findStudentById(id)).withSelfRel(),
                linkTo(methodOn(StudentController.class).deleteStudent(id)).withRel("delete"),
                linkTo(methodOn(AssessmentController.class).getAssessmentsByStudentId(student.getId())).withRel("assessments"),
                linkTo(methodOn(StudentController.class).findAllStudents()).withRel("all_students"));

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@Valid @PathVariable Long id,
                                                 @RequestBody StudentRecord studentRecord) {

        Student student = studentService.findById(id);
        Student newStudent = new Student(id, studentRecord.name(),
                studentRecord.email(), student.getAssessments());
        Student updatedStudent = studentService.update(newStudent);
        updatedStudent.add(linkTo(methodOn(StudentController.class).findStudentById(id)).withSelfRel(),
                linkTo(methodOn(StudentController.class).findAllStudents()).withRel("all_students"));

        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        Student deletedStudent = studentService.deleteById(id);
        deletedStudent.add(linkTo(methodOn(StudentController.class).findAllStudents()).withRel("all_students"));

        return new ResponseEntity<>(deletedStudent, HttpStatus.OK);
    }
}
