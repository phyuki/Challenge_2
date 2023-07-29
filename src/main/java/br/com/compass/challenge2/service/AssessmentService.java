package br.com.compass.challenge2.service;

import br.com.compass.challenge2.entity.Assessment;
import br.com.compass.challenge2.repository.AssessmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class AssessmentService implements CrudService<Assessment> {

    private AssessmentRepository assessmentRepository;

    @Autowired
    public AssessmentService(AssessmentRepository repository) {
        this.assessmentRepository = repository;
    }

    public Assessment save(Assessment assessment) {
        if (assessment.getId() == null) {
            return assessmentRepository.save(assessment);
        } else {
            throw new IllegalArgumentException("The \"id\" attribute is not allowed when creating a new assessment.");
        }
    }
    public Assessment findById(Long id) {
        Optional<Assessment> optionalAssessment = assessmentRepository.findById(id);
        if (optionalAssessment.isPresent()) {
            return optionalAssessment.get();
        } else {
            throw new EntityNotFoundException("Assessment does not exist with id: " + id);
        }
    }

    public List<Assessment> findAll() {
        return assessmentRepository.findAll();
    }


    public Assessment update(Assessment assessment) {
        if (assessmentRepository.existsById(assessment.getId())) {
            return assessmentRepository.save(assessment);
        } else {
            throw new EntityNotFoundException("Assessment does not exist with id: " + assessment.getId());
        }
    }

    public void deleteById(Long id) {
        try {
            assessmentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Assessment does not exist with id: " + id);
        }
    }
    @Override
    public void delete(Assessment assessment) {
        assessmentRepository.delete(assessment);
    }
}
