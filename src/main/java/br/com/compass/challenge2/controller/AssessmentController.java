package br.com.compass.challenge2.controller;
import br.com.compass.challenge2.dto.AssessmentDTO;
import br.com.compass.challenge2.entity.Assessment;
import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.service.AssessmentService;
import br.com.compass.challenge2.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {
    private final AssessmentService assessmentService;
    private final StudentService studentService;

    @Autowired
    public AssessmentController(AssessmentService assessmentService, StudentService studentService) {
        this.assessmentService = assessmentService;
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Assessment> createAssessment(@Valid @RequestBody AssessmentDTO assessmentDto) {
        Student student = studentService.findById(assessmentDto.getStudentId());

        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Assessment createdAssessment = new Assessment(null, student, assessmentDto.getActivityName(), assessmentDto.getGrade());
        createdAssessment = assessmentService.save(createdAssessment);
        return new ResponseEntity<>(createdAssessment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assessment> updateAssessment(@PathVariable Long id, @RequestBody AssessmentDTO assessmentDTO) {
        Assessment existingAssessment = assessmentService.findById(id);
        Student student = studentService.findById(assessmentDTO.getStudentId());
        if (existingAssessment != null) {

            if (!existingAssessment.getStudent().getId().equals(student.getId())) {
                existingAssessment.setStudent(student);
            }

            if (student == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            existingAssessment.setActivityName(assessmentDTO.getActivityName());
            existingAssessment.setGrade(assessmentDTO.getGrade());

            existingAssessment = assessmentService.update(existingAssessment);
            return new ResponseEntity<>(existingAssessment, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Assessment> updatePartialAssessment(@PathVariable Long id, @RequestBody AssessmentDTO assessmentDTO) {
        Assessment existingAssessment = assessmentService.findById(id);

        if (existingAssessment != null) {

            if (assessmentDTO.getActivityName() != null) {
                existingAssessment.setActivityName(assessmentDTO.getActivityName());
            }
            if(assessmentDTO.getGrade() != null) {
                existingAssessment.setGrade(assessmentDTO.getGrade());
            }
            if (assessmentDTO.getStudentId() != null) {
                Student student = studentService.findById(assessmentDTO.getStudentId());

                if(student == null){
                     return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                existingAssessment.setStudent(student);
            }

            Assessment updatedAssessment = assessmentService.update(existingAssessment);
            return new ResponseEntity<>(updatedAssessment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assessment> getAssessmentById(@PathVariable Long id) {
        Assessment assessment = assessmentService.findById(id);
        if (assessment != null) {
            return new ResponseEntity<>(assessment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssessment(@PathVariable Long id) {
        Assessment existingAssessment = assessmentService.findById(id);
        if (existingAssessment != null) {
            assessmentService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Assessment>> getAllAssessments() {
        List<Assessment> assessments = assessmentService.findAll();
        if (!assessments.isEmpty()) {
            return new ResponseEntity<>(assessments, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
