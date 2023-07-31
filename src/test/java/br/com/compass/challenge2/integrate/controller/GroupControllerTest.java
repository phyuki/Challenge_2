package br.com.compass.challenge2.integrate.controller;

import br.com.compass.challenge2.config.ConfigTest;
import br.com.compass.challenge2.entity.Group;
import br.com.compass.challenge2.repository.GroupRepository;
import br.com.compass.challenge2.service.GroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc(addFilters = false)
public class GroupControllerTest implements ConfigTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupRepository groupRepository;

    private Group group;

    @BeforeEach
    public void setup(){
        group = Group.builder()
                .id(1L)
                .name("Spring Boot")
                .organizers(new ArrayList<>())
                .students(new ArrayList<>())
                .build();
    }

    @AfterEach
    public void refresh() {
        groupRepository.deleteAll();
    }

    @Test
    void createSampleGroup() throws Exception {
        mockMvc.perform(post("/api/groups")
                        .content(objectMapper.writeValueAsString(group))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Spring Boot"));
    }

    @Test
    void findAllGroups() throws Exception {
        groupService.save(group);

        Group group2 = Group.builder()
                .name("AWS")
                .organizers(new ArrayList<>())
                .students(new ArrayList<>())
                .build();
        groupService.save(group2);

        mockMvc.perform(get("/api/groups"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Spring Boot"))
                .andExpect(jsonPath("$[1].name").value("AWS"));
    }

    @Test
    void findExistingGroupById() throws Exception {
        groupService.save(group);

        mockMvc.perform(get("/api/groups/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Spring Boot"));
    }

    @Test
    void findNonExistingGroupById() throws Exception {
        mockMvc.perform(get("/api/groups/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateExistingGroup() throws Exception {
        groupService.save(group);
        group.setName("AWS");

        mockMvc.perform(put("/api/groups/1")
                        .content(objectMapper.writeValueAsString(group))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("AWS"));
    }

    @Test
    void updateNonExistingGroup() throws Exception {

        mockMvc.perform(put("/api/groups/1")
                        .content(objectMapper.writeValueAsString(group))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteExistingGroup() throws Exception {
        groupService.save(group);

        mockMvc.perform(delete("/api/groups/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Spring Boot"));
    }

    @Test
    void deleteNonExistentGroup() throws Exception {
        mockMvc.perform(delete("/api/groups/1"))
                .andExpect(status().isNotFound());
    }

}
