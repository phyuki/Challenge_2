package br.com.compass.challenge2.repository;

import br.com.compass.challenge2.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByName(String name);
    @Transactional
    Long deleteByName(String name);
}
