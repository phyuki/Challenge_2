package br.com.compass.challenge2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.*;

import org.springframework.hateoas.RepresentationModel;

@Table(name = "squads")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Squad extends RepresentationModel<Squad> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "squad_name", nullable = false)
    @NotBlank(message = "Nome do squad não pode ser vazio")
    private String squadName;

    @OneToMany(mappedBy = "squad", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Student> students;  
        
    
    
}
