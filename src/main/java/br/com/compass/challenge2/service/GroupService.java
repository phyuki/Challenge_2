package br.com.compass.challenge2.service;

import br.com.compass.challenge2.entity.Group;
import br.com.compass.challenge2.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService{

    @Autowired
    private GroupRepository groupRepository;

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public Group findById(Long id) {
        Optional<Group> result = groupRepository.findById(id);
        Group resultGroup = null;
        if(result.isPresent()){
            resultGroup = result.get();
        }
        else{
            throw new RuntimeException("Did not find any group with id: "+id);
        }
        return resultGroup;
    }

    public Group findDistinctByName(String name) {
        return groupRepository.findDistinctByName(name);
    }

    public Group save(Group group) {
        return groupRepository.save(group);
    }

    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }

    public Long deleteByName(String name) {
        return groupRepository.deleteByName(name);
    }
}
