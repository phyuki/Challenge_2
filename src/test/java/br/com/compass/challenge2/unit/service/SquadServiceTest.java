package br.com.compass.challenge2.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.compass.challenge2.config.ConfigTest;
import br.com.compass.challenge2.entity.Squad;
import br.com.compass.challenge2.service.SquadService;

@SpringBootTest
public class SquadServiceTest implements ConfigTest {


    @Autowired
    private SquadService squadService;

    @Test
    public void testSaveSquad() {
        // Criar uma nova Squad para teste
        Squad squad = new Squad();
        squad.setSquadName("JEDGP");

        // Salvar a Squad no banco de dados
        Squad savedSquad = squadService.save(squad);

        // Verificar se a Squad foi salva com sucesso
        assertNotNull(savedSquad.getId());
        assertEquals("JEDGP", savedSquad.getSquadName());
    }

    @Test
    public void testFindAllSquads() {
        // Cria algumas Squads para teste
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
        // Cria uma nova Squad para teste.
        Squad squad = new Squad();
        squad.setSquadName("Squad Teste 4");

        // Salva a Squad no banco de dados.
        Squad savedSquad = squadService.save(squad);

        // Atualiza o nome da Squad.
        savedSquad.setSquadName("Squad Atualizada");
        Squad updatedSquad = squadService.update(savedSquad);

        // Verifica se a Squad foi atualizada de forma correta.
        assertEquals("Squad Atualizada", updatedSquad.getSquadName());
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

    // Teste para o filtro de pesquisa a partir do nome da Squad
    @Test
    public void testFindBySquadNameContainingIgnoreCase() {
        // Cria algumas Squads para teste.
        Squad squad1 = new Squad();
        squad1.setSquadName("Squad Teste 6");

        Squad squad2 = new Squad();
        squad2.setSquadName("Squad Teste 7");

        squadService.save(squad1);
        squadService.save(squad2);

        // Pesquisa Squads a partir do nome.
        List<Squad> foundSquads = squadService.findBySquadNameContainingIgnoreCase("Teste");

        // Verifica se as Squads foram encontradas com éxito.
        assertNotNull(foundSquads);
        assertEquals(2, foundSquads.size());
    }
   
}