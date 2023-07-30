package br.com.compass.challenge2.repository;

import br.com.compass.challenge2.entity.Organizer;
import br.com.compass.challenge2.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Long> {

    // Método para pesquisar organizadores pelo nome (filtro)
    List<Organizer> findByNameContainingIgnoreCase(String name);

    // Método para pesquisar organizadores pelo email (filtro)
    List<Organizer> findByEmailContainingIgnoreCase(String email);

    // Método para pesquisar organizadores por grupos (filtro)
    @Query("SELECT o FROM Organizer o JOIN o.groups g WHERE g.name = :groupName")
    List<Organizer> findByGroupName(@Param("groupName") String groupName);

    // Método para pesquisar organizadores por função (filtro)
    @Query("SELECT o FROM Organizer o JOIN o.roles r WHERE r = :role")
    List<Organizer> findByRole(@Param("role") Role role);

    // Método para pesquisar organizadores e ordenar pelo nome
    List<Organizer> findAllByOrderByNameAsc();

    // Método para pesquisar organizadores e ordenar pelo email
    List<Organizer> findAllByOrderByEmailAsc();

    // Método para pesquisar organizadores e ordenar pelo ID
    List<Organizer> findAllByOrderByIdAsc();

}
