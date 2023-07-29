package br.com.compass.challenge2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/squad")
public class SquadController {
	private final SquadService squadService;

	@Autowired
	public SquadController(SquadService squadService) {
        this.squadService = squadService;
    }

	@GetMapping("/all")
	public ResponseEntity<List<Squad>> findAllSquads(){
		List<Squad> squads = squadService.findAll();
		return ResponseEntity.ok(squads);		
	}
		
	@GetMapping("/{id}")
	public ResponseEntity<Squad> findSquadById(@PathVariable Long id){
		Squad squad = squadService.findById(id);
		if (squad != null) {
			return ResponseEntity.ok(squad);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Squad> createSquad(@RequestBody Squad squad){
		Squad createSquad = squadService.save(squad);
		return ResponseEntity.status(HttpStatus.CREATED).body(createSquad);
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Squad> updateSquad(@PathVariable Long id, @RequestBody Squad squad) {
        Squad updatedSquad = squadService.update(id, squad);
        if (updatedSquad != null) {
            return ResponseEntity.ok(updatedSquad);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteSquad(@PathVariable Long id) {
        squadService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

	
	


