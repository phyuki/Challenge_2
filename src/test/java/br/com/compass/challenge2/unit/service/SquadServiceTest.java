package br.com.compass.challenge2.unit.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.compass.challenge2.config.ConfigTest;
import br.com.compass.challenge2.entity.Squad;
import br.com.compass.challenge2.service.SquadService;
import jakarta.persistence.EntityNotFoundException;


@SpringBootTest
public class SquadServiceTest implements ConfigTest {


    @Autowired
    private SquadService squadService;

    @Test
    public void testSaveSquad() {
        
        Squad squad = new Squad();
        squad.setSquadName("JEDGP");
   
        Squad savedSquad = squadService.save(squad);       
        assertNotNull(savedSquad.getId());
        assertEquals("JEDGP", savedSquad.getSquadName());
    }

    @Test
    public void testFindAllSquads() {
        
        Squad squad1 = new Squad();
        squad1.setSquadName("Squad Teste 1");

        Squad squad2 = new Squad();
        squad2.setSquadName("Squad Teste 2");

        squadService.save(squad1);
        squadService.save(squad2);
     
        List<Squad> squads = squadService.findAll();

        assertNotNull(squads);
        assertEquals(2, squads.size());
    }

    @Test
    public void testFindSquadById() {      
        Squad squad = new Squad();
        squad.setSquadName("Squad Teste 3");      
        Squad savedSquad = squadService.save(squad);
    
        Squad foundSquad = squadService.findById(savedSquad.getId());
        assertNotNull(foundSquad);
        assertEquals(savedSquad.getId(), foundSquad.getId());
    }

    @Test
    public void testUpdateSquad() {    	 
        Squad squad = new Squad();
        squad.setSquadName("Squad Inicial");
        Squad savedSquad = squadService.save(squad);
     
        Squad updatedSquad = new Squad();
        updatedSquad.setId(savedSquad.getId());
        updatedSquad.setSquadName("Squad Atualizada");
        
        Squad result = squadService.update(updatedSquad);
    
        assertNotNull(result);
    
        assertEquals(updatedSquad.getId(), result.getId());
        assertEquals(updatedSquad.getSquadName(), result.getSquadName());
    }        
    
    @Test
    public void testUpdateSquadProperties() {       
        Squad squad = new Squad();
        squad.setSquadName("Squad Inicial");
        Squad savedSquad = squadService.save(squad);
      
        Squad updatedSquad = new Squad();
        updatedSquad.setId(savedSquad.getId());
        updatedSquad.setSquadName("Squad Atualizada");
        
        Squad result = squadService.update(savedSquad.getId(), updatedSquad);
        assertNotNull(result);        
        assertEquals(updatedSquad.getId(), result.getId());
        assertEquals(updatedSquad.getSquadName(), result.getSquadName()); 
        assertEquals(savedSquad.getStudents(), result.getStudents());
    }

    @Test
    public void testDeleteSquadById() {      
        Squad squad = new Squad();
        squad.setSquadName("Squad para Deletar");
    
        Squad savedSquad = squadService.save(squad);
       
        Squad deletedSquad = squadService.deleteById(savedSquad.getId());
        
        assertNotNull(deletedSquad);
        assertEquals(savedSquad.getId(), deletedSquad.getId());       
       
    }
    
    @Test
    public void testDeleteNonExistentSquad() {       
        Long nonExistentSquadId = 999L;      
        try {
            squadService.deleteById(nonExistentSquadId);          
            fail("Expected EntityNotFoundException, but no exception was thrown.");
        } catch (EntityNotFoundException e) {            
            assertTrue(e.getMessage().contains("Squad does not exist with id: " + nonExistentSquadId));
        }
    }

    // Teste para o filtro de pesquisa a partir do nome da Squad
    @Test
    public void testFindBySquadNameContainingIgnoreCase() {       
        Squad squad1 = new Squad();
        squad1.setSquadName("Squad Teste 6");

        Squad squad2 = new Squad();
        squad2.setSquadName("Squad Teste 7");

        squadService.save(squad1);
        squadService.save(squad2);

      
        List<Squad> foundSquads = squadService.findBySquadNameContainingIgnoreCase("Teste");       
        assertNotNull(foundSquads);
        assertEquals(2, foundSquads.size());
    }
   
}