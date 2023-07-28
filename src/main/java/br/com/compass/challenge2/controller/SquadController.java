package br.com.compass.challenge2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.compass.challenge2.entity.Squad;
import br.com.compass.challenge2.service.SquadService;

@RestController
@RequestMapping
public class SquadController {
	
	private final SquadService squadService;
	
	public SquadController(SquadService squadService) {
        this.squadService = squadService;
    }
		
	// Mostrar todos squads.
	@GetMapping("/squads")
	public ResponseEntity<List<Squad>> getAllSquads(){
		List<Squad> squads = squadService.getAllSquads();
		return ResponseEntity.ok(squads);		
	}
		
	// Mostrar um squad específico pelo id.
	@GetMapping("/{id}")
	public ResponseEntity<Squad> getSquadById(@PathVariable Long id){
		Squad squad = squadService.getSquadById(id);
		if (squad != null) {
			return ResponseEntity.ok(squad);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	// Criar novo squad.
	@PostMapping
	public ResponseEntity<Squad> createSquad(@RequestBody Squad squad){
		Squad createSquad = squadService.createSquad(squad);
		return ResponseEntity.status(HttpStatus.CREATED).body(createSquad);
	}
	
	// Atualizar um squad.
	@PutMapping("/{id}")
    public ResponseEntity<Squad> updateSquad(@PathVariable Long id, @RequestBody Squad squad) {
        Squad updatedSquad = squadService.updateSquad(id, squad);
        if (updatedSquad != null) {
            return ResponseEntity.ok(updatedSquad);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	// Excluir squad existente.
	 @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteSquad(@PathVariable Long id) {
	        boolean deleted = squadService.deleteSquad(id);
	        if (deleted) {
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
}

	
	


