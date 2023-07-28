package br.com.compass.challenge2.service;

import br.com.compass.challenge2.entity.Assessment;
import br.com.compass.challenge2.repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssessmentService {

    private AssessmentRepository assessmentRepository;

    @Autowired
    public AssessmentService(AssessmentRepository repository) {
        this.assessmentRepository = repository;
    }
    public Assessment assessmentById(Long id) {
        Optional<Assessment> optionalAssessment = assessmentRepository.findById(id);
        return optionalAssessment.orElse(null);
    }
    public Assessment createAssessment(Assessment assessment) {
        return assessmentRepository.save(assessment);
    }
    public Assessment updateAssessment(Assessment assessment) {
        Assessment assessment1 = assessmentById(assessment.getId());

        return assessment1 != null? assessmentRepository.save(assessment) : null;
    }
    public void deleteAssessment(Long id) {
        assessmentRepository.deleteById(id);
    }
    public List<Assessment> getAllAssessments() {
        return assessmentRepository.findAll();
    }
}
