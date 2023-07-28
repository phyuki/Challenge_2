package br.com.compass.challenge2.controller;

import br.com.compass.challenge2.entity.Group;
import br.com.compass.challenge2.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {

    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/all")
    public List<Group> findAll(){
        return groupService.findAll();
    }

    @GetMapping("/{groupId}")
    public Group findGroupById(@PathVariable Long groupId){
        Group group = groupService.findById(groupId);
        if(group == null){
            throw new RuntimeException("Group not found! Id: "+groupId);
        }
        return group;
    }

    @GetMapping("/{groupName}")
    public Group findGroupByName(@PathVariable String groupName){
        Group group = groupService.findDistinctByName(groupName);
        if(group == null){
            throw new RuntimeException("Group not found! Name: "+groupName);
        }
        return group;
    }

    @PostMapping
    public Group addGroup(@RequestBody Group postGroup){
        postGroup.setId(0L);
        Group newGroup = groupService.save(postGroup);
        return newGroup;
    }

    @PutMapping
    public Group updateGroup(@RequestBody Group putGroup){
        Group newGroup = groupService.save(putGroup);
        return newGroup;
    }

    @DeleteMapping("/{groupId}")
    public String deleteGroupById(@PathVariable Long groupId){
        //Group checkGroup = groupService.findById(groupId);
        groupService.deleteById(groupId);
        return "Deleted employee with id: "+groupId;
    }

    @DeleteMapping("/{groupName}")
    public String deleteGroupByName(@PathVariable String groupName){
        //Group checkGroup = groupService.findDistinctByName(groupName);
        groupService.deleteByName(groupName);
        return "Deleted employee with name: "+groupName;
    }

}
