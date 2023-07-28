package br.com.compass.challenge2.repository;

import br.com.compass.challenge2.entity.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.compass.challenge2.entity.Student;

public interface  OrganizerRepository extends JpaRepository<Student, Long> {

    void delete(Long id);

    void update(Organizer organizer);
}
