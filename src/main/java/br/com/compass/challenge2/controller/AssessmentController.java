package br.com.compass.challenge2.controller;

import br.com.compass.challenge2.entity.Assessment;
import br.com.compass.challenge2.service.AssessmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/assessment")
public class AssessmentController {
    private final AssessmentService assessmentService;

    @Autowired
    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @PostMapping
    public ResponseEntity<Assessment> createAssessment(@Valid  @RequestBody Assessment assessment) {
        Assessment createdAssessment = assessmentService.save(assessment);
        return new ResponseEntity<>(createdAssessment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assessment> updateAssessment(@Valid @PathVariable Long id, @RequestBody Assessment assessment) {
        Assessment existingAssessment = assessmentService.findById(id);
        if (existingAssessment != null) {
            assessment.setId(id);
            Assessment updatedAssessment = assessmentService.update(assessment);
            return new ResponseEntity<>(updatedAssessment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    @GetMapping("/all")
    public ResponseEntity<List<Assessment>> getAllAssessments() {
        List<Assessment> assessments = assessmentService.findAll();
        return new ResponseEntity<>(assessments, HttpStatus.OK);
    }
}
