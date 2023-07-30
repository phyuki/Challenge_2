package br.com.compass.challenge2.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.compass.challenge2.config.ConfigTest;
import br.com.compass.challenge2.entity.Squad;
import br.com.compass.challenge2.service.SquadService;
import br.com.compass.challenge2.entity.Student;

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
        // Criar algumas Squads para teste
        Squad squad1 = new Squad();
        squad1.setSquadName("Squad Teste 1");

        Squad squad2 = new Squad();
        squad2.setSquadName("Squad Teste 2");

        squadService.save(squad1);
        squadService.save(squad2);

        // Mostrar todas as Squads do banco de dados.
        List<Squad> squads = squadService.findAll();

        // Verifica se a lista de Squads não está vazia e se possui o tamanho correto.
        assertNotNull(squads);
        assertEquals(2, squads.size());
    }

    @Test
    public void testFindSquadById() {
        // Cria uma nova Squad para teste.
        Squad squad = new Squad();
        squad.setSquadName("Squad Teste 3");

        // Salva a Squad no banco de dados.
        Squad savedSquad = squadService.save(squad);

        // Obtem a Squad pelo seu Id.
        Squad foundSquad = squadService.findById(savedSquad.getId());

        // Verifica se a Squad foi encontrada.
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
        // Cria uma nova Squad para teste.
        Squad squad = new Squad();
        squad.setSquadName("Squad Teste 5");

        // Salva a Squad no banco de dados.
        Squad savedSquad = squadService.save(squad);

        // Deleta a Squad pelo Id.
        Squad deletedSquad = squadService.deleteById(savedSquad.getId());

        // Verifica se a Squad foi excluída com éxito.
        assertNotNull(deletedSquad);
        assertEquals(savedSquad.getId(), deletedSquad.getId());

        // Tenta encontrar a Squad novamente.
        Squad notFoundSquad = squadService.findById(savedSquad.getId());
        assertNull(notFoundSquad);
        // Deve retornar um valor null.
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

    // Teste para o filtro de pesquisa por nome de um integrante da Squad.
    @Test
    public void testFindByStudentNameContainingIgnoreCase() {
        // Cria uma nova Squad com alguns estudantes para realizar o teste.
        Squad squad = new Squad();
        squad.setSquadName("Squad com Estudantes");

        Student student1 = new Student();
        student1.setName("João da Silva");

        Student student2 = new Student();
        student2.setName("Maria Santos");

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        squad.setStudents(students);

        // Salva a Squad no banco de dados.
        Squad savedSquad = squadService.save(squad);

        // Pesquisa por Squads a partir do nome do estudante.
        List<Squad> foundSquads = squadService.findByStudentNameContainingIgnoreCase("João");

        // Verifica se as Squads foram encontradas corretamente.
        assertNotNull(foundSquads);
        assertEquals(1, foundSquads.size());
        assertEquals(savedSquad.getId(), foundSquads.get(0).getId());
    }
}