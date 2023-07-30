package br.com.compass.challenge2.repository;
import br.com.compass.challenge2.entity.Assessment;
import br.com.compass.challenge2.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    List<Assessment> findByStudent(Student student);
}
