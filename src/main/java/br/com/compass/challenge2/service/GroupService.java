package br.com.compass.challenge2.service;

import br.com.compass.challenge2.entity.Group;
import br.com.compass.challenge2.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService implements CrudService<Group> {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public Group findById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Override
    public Group save(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group update(Group group) {
        if (groupRepository.existsById(group.getId())) {
            return groupRepository.save(group);
        } else {
            throw new IllegalArgumentException("Group with id " + group.getId() + " not found.");
        }
    }

    @Override
    public void delete(Group group) {
        groupRepository.delete(group);
    }

    @Override
    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }

    public Group findDistinctByName(String name) {
        return groupRepository.findByName(name);
    }

    public Long deleteByName(String name) {
        return groupRepository.deleteByName(name);
    }
}
