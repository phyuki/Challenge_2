package br.com.compass.challenge2.controller;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.records.SquadRecord;
import br.com.compass.challenge2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.compass.challenge2.entity.Squad;
import br.com.compass.challenge2.service.SquadService;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/squads")
public class SquadController {
	private final SquadService squadService;
	private final StudentService studentService;

	@Autowired
	public SquadController(SquadService squadService, StudentService studentService) {
		this.squadService = squadService;
		this.studentService = studentService;
	}

	@GetMapping
	public ResponseEntity<List<Squad>> findAllSquads() {
        List<Squad> squads = squadService.findAll();

        squads.forEach(squad -> squad.add(
                linkTo(methodOn(SquadController.class).getSquadById(squad.getId())).withSelfRel()));

        return new ResponseEntity<>(squads, HttpStatus.OK);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Squad>> getSquadById(@PathVariable Long id) {		
	     	Squad squad = squadService.findById(id);
	    	if (squad != null) {
	    		 EntityModel<Squad> squadModel = EntityModel.of(squad).add(
	                     linkTo(methodOn(SquadController.class).getSquadById(id)).withSelfRel(),
	                     linkTo(methodOn(SquadController.class).findAllSquads()).withRel("all_squads"),
	                     linkTo(methodOn(SquadController.class).updateSquad(id, null)).withRel("update"),
	                     linkTo(methodOn(SquadController.class).deleteSquad(id)).withRel("delete"));
            return ResponseEntity.status(HttpStatus.OK).body(squadModel);
          } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
          }
	}
   	
	
	@PostMapping
	public ResponseEntity<?> createSquad(@RequestBody @Valid SquadRecord squadRecord) {

		List<Student> students = new ArrayList<>();
		if(squadRecord.studentIDs() != null) {
			for (Long studentID : squadRecord.studentIDs()) {
				students.add(studentService.findById(studentID));
			}
		}
		Squad squad = new Squad(0L, squadRecord.squadName(), students);
		Squad createdSquad = squadService.save(squad);

		EntityModel<Squad> squadModel = EntityModel.of(createdSquad).add(
	                linkTo(methodOn(SquadController.class).getSquadById(createdSquad.getId())).withSelfRel(),
	                linkTo(methodOn(SquadController.class).findAllSquads()).withRel("all_squads"),
	                linkTo(methodOn(SquadController.class).updateSquad(createdSquad.getId(), null)).withRel("update"),
	                linkTo(methodOn(SquadController.class).deleteSquad(createdSquad.getId())).withRel("delete")
	        );
		return new ResponseEntity<>(squadModel, HttpStatus.CREATED);
				
	}

	
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<Squad>> updateSquad(@PathVariable Long id,
														  @Valid @RequestBody SquadRecord squadRecord) {

		List<Student> students = new ArrayList<>();
		if(squadRecord.studentIDs() != null) {
			for (Long studentID : squadRecord.studentIDs()) {
				students.add(studentService.findById(studentID));
			}
		}
		Squad squad = new Squad(id, squadRecord.squadName(), students);
        Squad updatedSquad = squadService.update(id, squad);

        if (updatedSquad != null) {
            EntityModel<Squad> squadModel = EntityModel.of(updatedSquad).add(
                    linkTo(methodOn(SquadController.class).getSquadById(id)).withSelfRel(),
                    linkTo(methodOn(SquadController.class).findAllSquads()).withRel("all_squads")
            );

            return ResponseEntity.status(HttpStatus.OK).body(squadModel);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
	
	 @DeleteMapping("/{id}")
	    public ResponseEntity<EntityModel<Squad>> deleteSquad(@PathVariable Long id) {
	        Squad squad = squadService.findById(id);

	        if (squad != null) {
	            Squad deletedSquad = squadService.deleteById(id);
	            EntityModel<Squad> squadModel = EntityModel.of(deletedSquad).add(
	                    linkTo(methodOn(SquadController.class).findAllSquads()).withRel("all_squads")
	            );
	            return ResponseEntity.status(HttpStatus.OK).body(squadModel);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	        }
	    }
	
	
	
	@GetMapping("/search/squad-name")
	    public ResponseEntity<List<Squad>> findBySquadName(@RequestParam("name") String squadName) {
	        List<Squad> squads = squadService.findBySquadNameContainingIgnoreCase(squadName);
	        return ResponseEntity.status(HttpStatus.OK).body(squads);
	    }
	
     
	    
	
}
