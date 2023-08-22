package br.com.compass.challenge2.repository;

import br.com.compass.challenge2.entity.Group;
import br.com.compass.challenge2.records.GroupRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
