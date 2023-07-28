package br.com.compass.challenge2.repository;

import br.com.compass.challenge2.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    public Group findDistinctByName(String name);

    @Transactional
    Long deleteByName(String name);
}
