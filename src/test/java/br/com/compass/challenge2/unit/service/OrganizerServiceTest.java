package br.com.compass.challenge2.unit.service;

import br.com.compass.challenge2.config.ConfigTest;
import br.com.compass.challenge2.entity.Group;
import br.com.compass.challenge2.entity.Organizer;
import br.com.compass.challenge2.entity.Role;
import br.com.compass.challenge2.repository.GroupRepository;
import br.com.compass.challenge2.repository.OrganizerRepository;
import br.com.compass.challenge2.repository.StudentRepository;
import br.com.compass.challenge2.service.GroupService;
import br.com.compass.challenge2.service.OrganizerService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrganizerServiceTest implements ConfigTest {

    private OrganizerService organizerService;
    @MockBean
    private OrganizerRepository organizerRepository;
    private Organizer organizer;

    @BeforeEach
    public void setUp(){
        organizerService = new OrganizerService(organizerRepository);
        organizer = new Organizer();
        organizer.setId(1L);
        organizer.setName("Victor");
        organizer.setEmail("victor@email.com");
        organizer.setGroups(new ArrayList<>());
        organizer.setRoles(new ArrayList<>());
    }

    @Test
    public void saveNewOrganizer(){
        Group group = Group.builder()
                .id(1L)
                .name("Spring Boot")
                .organizers(new ArrayList<>())
                .students(new ArrayList<>())
                .build();
        organizer.setGroups(Arrays.asList(group));
        organizer.setRoles(Arrays.asList(Role.COORDINATOR, Role.SCRUM_MASTER));

        when(organizerRepository.save(any())).thenReturn(organizer);
        Organizer savedOrg = organizerService.save(organizer);

        verify(organizerRepository, times(1)).save(any(Organizer.class));
        assertEquals("Victor", savedOrg.getName());
        assertEquals("victor@email.com", savedOrg.getEmail());
    }

    @Test
    public void saveNewOrganizerWithoutGroup(){
        when(organizerRepository.save(any())).thenReturn(organizer);
        Organizer savedOrg = organizerService.save(organizer);

        verify(organizerRepository, times(1)).save(any(Organizer.class));
        assertEquals("Victor", savedOrg.getName());
        assertEquals("victor@email.com", savedOrg.getEmail());
    }

    @Test
    void findOrganizer() {
        when(organizerRepository.findById(1L)).thenReturn(Optional.of(organizer));

        Organizer foundOrg = organizerService.findById(1L);

        assertEquals(1L, foundOrg.getId());
        assertEquals("Victor", foundOrg.getName());
        assertEquals("victor@email.com", foundOrg.getEmail());
        verify(organizerRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void findNonExistingOrganizer() {
        when(organizerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> organizerService.findById(1L));
    }

    @Test
    void findAllOrganizers() {
        Organizer newOrg = new Organizer();
        organizer.setId(2L);
        organizer.setName("Maria");
        organizer.setEmail("maria@email.com");

        List<Organizer> allOrgs = new ArrayList<>(Arrays.asList(organizer, newOrg));
        when(organizerRepository.findAll()).thenReturn(allOrgs);
        List<Organizer> findAllOrgs = organizerService.findAll();

        assertThat(findAllOrgs).usingRecursiveComparison().isEqualTo(allOrgs);
        verify(organizerRepository, times(1)).findAll();
    }

    @Test
    void updateOrganizer() {
        when(organizerRepository.findById(1L)).thenReturn(Optional.of(organizer));
        when(organizerRepository.save(any())).thenReturn(organizer);

        Organizer updatedOrg = organizerService.update(organizer);

        assertEquals(1L, updatedOrg.getId());
        assertEquals("Victor", updatedOrg.getName());
        assertEquals("victor@email.com", updatedOrg.getEmail());
        verify(organizerRepository, times(1)).save(organizer);
    }

    @Test
    void updateNonExistingOrganizer() {
        when(organizerRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> organizerService.update(organizer));
    }

    @Test
    void deleteOrganizerWithoutGroups() {
        when(organizerRepository.findById(1L)).thenReturn(Optional.of(organizer));

        Organizer deletedOrganizer = organizerService.deleteById(1L);

        assertThat(deletedOrganizer).usingRecursiveComparison().isEqualTo(organizer);
        verify(organizerRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteOrganizerWithAllFields() {
        Group group = Group.builder()
                .id(1L)
                .name("Spring Boot")
                .organizers(new ArrayList<>())
                .students(new ArrayList<>())
                .build();
        organizer.setGroups(Arrays.asList(group));
        organizer.setRoles(Arrays.asList(Role.COORDINATOR, Role.SCRUM_MASTER));

        when(organizerRepository.findById(1L)).thenReturn(Optional.of(organizer));

        Organizer deletedOrganizer = organizerService.deleteById(1L);

        assertThat(deletedOrganizer).usingRecursiveComparison().isEqualTo(organizer);
        verify(organizerRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteNonExistingOrganizer() {
        when(organizerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> organizerService.deleteById(1L));
        verify(organizerRepository, times(1)).findById(any(Long.class));

    }
}
