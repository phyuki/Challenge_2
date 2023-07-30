package br.com.compass.challenge2.controller;
import br.com.compass.challenge2.dto.AssessmentDTO;
import br.com.compass.challenge2.entity.Assessment;
import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.service.AssessmentService;
import br.com.compass.challenge2.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@RestController
@RequestMapping("api/assessments")
public class AssessmentController {
    private final AssessmentService assessmentService;
    private final StudentService studentService;

    @Autowired
    public AssessmentController(AssessmentService assessmentService, StudentService studentService) {
        this.assessmentService = assessmentService;
        this.studentService = studentService;
    }
    @PostMapping
    public ResponseEntity<EntityModel<Assessment>> createAssessment(@Valid @RequestBody AssessmentDTO assessmentDto) {
        Student student = studentService.findById(assessmentDto.getStudentId());

        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Assessment createdAssessment = new Assessment(null, student, assessmentDto.getActivityName(), assessmentDto.getGrade());
        createdAssessment = assessmentService.save(createdAssessment);

        // Adicione links HATEOAS
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssessmentController.class).getAssessmentById(createdAssessment.getId())).withSelfRel();
        Link allAssessmentsLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssessmentController.class).getAllAssessments()).withRel("allAssessments");
        EntityModel<Assessment> assessmentModel = EntityModel.of(createdAssessment, selfLink, allAssessmentsLink);

        return new ResponseEntity<>(assessmentModel, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Assessment>> updateAssessment(@PathVariable Long id, @RequestBody AssessmentDTO assessmentDTO) {
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

            // Adicione links HATEOAS
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssessmentController.class).getAssessmentById(existingAssessment.getId())).withSelfRel();
            Link allAssessmentsLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssessmentController.class).getAllAssessments()).withRel("allAssessments");
            EntityModel<Assessment> assessmentModel = EntityModel.of(existingAssessment, selfLink, allAssessmentsLink);

            return new ResponseEntity<>(assessmentModel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<Assessment>> updatePartialAssessment(@PathVariable Long id, @RequestBody AssessmentDTO assessmentDTO) {
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

            // Adicione links HATEOAS
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssessmentController.class).getAssessmentById(updatedAssessment.getId())).withSelfRel();
            Link allAssessmentsLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssessmentController.class).getAllAssessments()).withRel("allAssessments");
            EntityModel<Assessment> assessmentModel = EntityModel.of(updatedAssessment, selfLink, allAssessmentsLink);

            return new ResponseEntity<>(assessmentModel, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Assessment>> getAssessmentById(@PathVariable Long id) {
        Assessment assessment = assessmentService.findById(id);
        if (assessment != null) {

            // Adicione links HATEOAS
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssessmentController.class).getAssessmentById(id)).withSelfRel();
            Link allAssessmentsLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssessmentController.class).getAllAssessments()).withRel("allAssessments");
            EntityModel<Assessment> assessmentModel = EntityModel.of(assessment, selfLink, allAssessmentsLink);

            return new ResponseEntity<>(assessmentModel, HttpStatus.OK);
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
    public ResponseEntity<CollectionModel<EntityModel<Assessment>>> getAllAssessments() {
        List<Assessment> assessments = assessmentService.findAll();
        if (!assessments.isEmpty()) {
            List<EntityModel<Assessment>> assessmentModels = assessments.stream()
                    .map(assessment -> EntityModel.of(assessment,
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssessmentController.class).getAssessmentById(assessment.getId())).withSelfRel()))
                    .collect(Collectors.toList());

            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssessmentController.class).getAllAssessments()).withSelfRel();
            CollectionModel<EntityModel<Assessment>> assessmentCollectionModel = CollectionModel.of(assessmentModels, selfLink);

            return new ResponseEntity<>(assessmentCollectionModel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<CollectionModel<EntityModel<Assessment>>> getAssessmentsByStudentId(@PathVariable Long id) {
        List<Assessment> assessments = assessmentService.getAssessmentsByStudentId(id);

        if (!assessments.isEmpty()) {
            List<EntityModel<Assessment>> assessmentModels = assessments.stream()
                    .map(assessment -> EntityModel.of(assessment,
                            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssessmentController.class).getAssessmentById(assessment.getId())).withSelfRel()))
                    .collect(Collectors.toList());

            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AssessmentController.class).getAssessmentsByStudentId(id)).withSelfRel();
            CollectionModel<EntityModel<Assessment>> assessmentCollectionModel = CollectionModel.of(assessmentModels, selfLink);

            return new ResponseEntity<>(assessmentCollectionModel, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
