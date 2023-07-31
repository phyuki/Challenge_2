package br.com.compass.challenge2.integrate.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.containsString;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.compass.challenge2.config.ConfigTest;
import br.com.compass.challenge2.entity.Squad;
import br.com.compass.challenge2.service.SquadService;


@AutoConfigureMockMvc(addFilters = false)
public class SquadControllerTest implements ConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SquadService squadService;

    @Autowired
    private ObjectMapper objectMapper;        
   
          

    @Test
    public void testCreateSquad() throws Exception {
        Squad squad = new Squad();
    	    squad.setSquadName("Squad Test");

        mockMvc.perform(post("/api/squads")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(squad)))
              .andExpect(status().isCreated())
              .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
              .andExpect(jsonPath("$.squadName").value("Squad Test"));
    	}
        
    @Test
    public void testFindAllSquads() throws Exception {
    	 Squad squad1 = new Squad();
    	    squad1.setSquadName("Squad Teste 1");
    	    squadService.save(squad1);

    	    Squad squad2 = new Squad();
    	    squad2.setSquadName("Squad Teste 2");
    	    squadService.save(squad2);

    	    mockMvc.perform(get("/api/squads"))
    	           .andExpect(status().isOk())
    	           .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    	           .andExpect(jsonPath("$[0].squadName").value("Squad Teste 1"))
    	           .andExpect(jsonPath("$[1].squadName").value("Squad Teste 2"));
    	}
    
    @Test
    public void testGetSquadById() throws Exception {
        
        Squad squad = new Squad();
        squad.setSquadName("Squad Test");
        squadService.save(squad);

        mockMvc.perform(get("/api/squads/{id}", squad.getId()))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
               .andExpect(jsonPath("$.squadName").value("Squad Test"))
               .andExpect(jsonPath("$._links.self.href").value(containsString("/api/squads/" + squad.getId())))
               .andExpect(jsonPath("$._links.all_squads.href").value(containsString("/api/squads")))
               .andExpect(jsonPath("$._links.update.href").value(containsString("/api/squads/" + squad.getId())))
               .andExpect(jsonPath("$._links.delete.href").value(containsString("/api/squads/" + squad.getId())));
    }    
    

    @Test
    public void testUpdateSquad() throws Exception {
       
        Squad squad = new Squad();
        squad.setSquadName("Squad Test");
        squadService.save(squad);

        // Atualiza o nome da Squad
        squad.setSquadName("Updated Squad");

        mockMvc.perform(put("/api/squads/{id}", squad.getId())
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(squad)))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaTypes.HAL_JSON_VALUE))
               .andExpect(jsonPath("$.squadName").value("Updated Squad"))
               .andExpect(jsonPath("$._links.self.href").value(containsString("/api/squads/" + squad.getId())))
               .andExpect(jsonPath("$._links.all_squads.href").value(containsString("/api/squads")));
    }

    @Test
    public void testDeleteSquad() throws Exception {
        
        Squad squad = new Squad();
        squad.setSquadName("Squad Test");
        squadService.save(squad);

        mockMvc.perform(delete("/api/squads/{id}", squad.getId()))
               .andExpect(status().isOk());
    }
    
    @Test
    public void testFindBySquadName() throws Exception {
        Squad squad1 = new Squad();
        squad1.setSquadName("Squad Teste 1");
        squadService.save(squad1);

        Squad squad2 = new Squad();
        squad2.setSquadName("Squad Teste 2");
        squadService.save(squad2);

        mockMvc.perform(get("/api/squads/search/squad-name")
               .param("name", "Test"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$[0].squadName").value("Squad Teste 1"))
               .andExpect(jsonPath("$[1].squadName").value("Squad Teste 2"));
    }

   
    
}