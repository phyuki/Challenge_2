package br.com.compass.challenge2.integrate.controller;

import br.com.compass.challenge2.config.ConfigTest;
import br.com.compass.challenge2.entity.Organizer;
import br.com.compass.challenge2.entity.Role;
import br.com.compass.challenge2.service.OrganizerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
public class OrganizerControllerTest implements ConfigTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrganizerService organizerService;

    private Organizer testOrganizer;

    @BeforeEach
    public void setUp() {
        testOrganizer = new Organizer();
        testOrganizer.setName("John Doe");
        testOrganizer.setEmail("john.doe@example.com");
        testOrganizer = organizerService.save(testOrganizer);
    }

    @Order(1)
    @Test
    public void testCreateOrganizer() throws Exception {
        Organizer newOrganizer = new Organizer();
        newOrganizer.setName("Jane Smith");
        newOrganizer.setEmail("jane.smith@example.com");

        mockMvc.perform(post("/organizers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newOrganizer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Jane Smith"))
                .andExpect(jsonPath("$.email").value("jane.smith@example.com"));
    }
    @Test
    public void testCreateOrganizerInvalidData() throws Exception {
        Organizer invalidOrganizer = new Organizer(); // Não definir dados obrigatórios, o que deve resultar em uma resposta de erro

        mockMvc.perform(post("/organizers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidOrganizer)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testUpdateOrganizer() throws Exception {
        testOrganizer.setName("Updated Name");
        testOrganizer.setEmail("updated.email@example.com");

        mockMvc.perform(put("/organizers/{id}", testOrganizer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testOrganizer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.email").value("updated.email@example.com"));
    }

    @Test
    public void testGetOrganizerById() throws Exception {
        mockMvc.perform(get("/organizers/{id}", testOrganizer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testGetOrganizerByIdNotFound() throws Exception {
        mockMvc.perform(get("/organizers/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteOrganizer() throws Exception {
        mockMvc.perform(delete("/organizers/{id}", testOrganizer.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteOrganizerNotFound() throws Exception {
        mockMvc.perform(delete("/organizers/{id}", 999L))
                .andExpect(status().isNotFound());
    }
    @Test
    public void testAssociateOrganizerWithRole() throws Exception {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.COORDINATOR);
        testOrganizer.setRoles(roles);

        mockMvc.perform(put("/organizers/{id}", testOrganizer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testOrganizer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roles").isArray())
                .andExpect(jsonPath("$.roles[0]").value("COORDINATOR"));
    }

    @Test
    public void testGetAllOrganizers() throws Exception {
        mockMvc.perform(get("/organizers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.organizers").isArray())
                .andExpect(jsonPath("$._embedded.organizers[0].name").value("John Doe"))
                .andExpect(jsonPath("$._embedded.organizers[0].email").value("john.doe@example.com"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetAllOrganizersWithMultipleOrganizers() throws Exception {
        Organizer anotherOrganizer = new Organizer();
        anotherOrganizer.setName("Jane Smith");
        anotherOrganizer.setEmail("jane.smith@example.com");
        organizerService.save(anotherOrganizer);

        mockMvc.perform(get("/organizers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.organizers").isArray())
                .andExpect(jsonPath("$._embedded.organizers", hasSize(2)))
                .andExpect(jsonPath("$._embedded.organizers[0].name").value("John Doe"))
                .andExpect(jsonPath("$._embedded.organizers[1].name").value("Jane Smith"))
                .andDo(MockMvcResultHandlers.print());
    }


}
