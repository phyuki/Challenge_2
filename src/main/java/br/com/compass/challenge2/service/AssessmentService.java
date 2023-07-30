package br.com.compass.challenge2.service;

import br.com.compass.challenge2.entity.Assessment;
import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AssessmentService implements CrudService<Assessment> {

    private AssessmentRepository assessmentRepository;
    private StudentService studentService;
    @Autowired
    public AssessmentService(AssessmentRepository repository, StudentService studentService) {
        this.assessmentRepository = repository;
        this.studentService = studentService;
    }

    @Override
    public Assessment save(Assessment assessment) {
        if (assessment.getId() == null) {
            return assessmentRepository.save(assessment);
        } else {
            throw new IllegalArgumentException("The \"id\" attribute is not allowed when creating a new assessment.");
        }
    }

    @Override
    public Assessment findById(Long id) {
        Optional<Assessment> optionalAssessment = assessmentRepository.findById(id);
        if (optionalAssessment.isPresent()) {
            return optionalAssessment.get();
        } else {
            throw new EntityNotFoundException("Assessment does not exist with id: " + id);
        }
    }

    @Override
    public List<Assessment> findAll() {
        return assessmentRepository.findAll();
    }

    @Override
    public Assessment update(Assessment assessment) {
        if (assessmentRepository.existsById(assessment.getId())) {
            return assessmentRepository.save(assessment);
        } else {
            throw new EntityNotFoundException("Assessment does not exist with id: " + assessment.getId());
        }
    }

    @Override
    public Assessment deleteById(Long id) {
        Assessment assessment;
        try {
            assessment = assessmentRepository.findById(id).get();
            assessmentRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Assessment does not exist with id: " + id);
        }

        return assessment;
    }
    public List<Assessment> getAssessmentsByStudentId(Long studentId) {
        Student student = studentService.findById(studentId);
        if (student == null) {
            throw new EntityNotFoundException("Student not found with id: " + studentId);
        }
        return assessmentRepository.findByStudent(student);
    }
}
