package br.com.compass.challenge2.repository;

import br.com.compass.challenge2.entity.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Long> {

    void deleteById(Long id);

}
